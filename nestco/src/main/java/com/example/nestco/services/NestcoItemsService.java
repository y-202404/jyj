package com.example.nestco.services;


import com.example.nestco.config.local.LocalUserDetails;
import com.example.nestco.models.dto.NestcoForm;
import com.example.nestco.models.entity.Category;
import com.example.nestco.models.entity.Member;
import com.example.nestco.models.entity.NestcoItems;
import com.example.nestco.dao.repository.NestcoItemsRepository;

import com.example.nestco.models.entity.QNestcoItems;
import com.example.nestco.services.helper.FilterHelper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class NestcoItemsService {

    private final NestcoItemsRepository nestcoItemsRepository;
    private final CategoryService categoryService;
    private final ItemThumbnailService itemThumbnailService;

    @PersistenceContext
    private EntityManager entityManager;

    // 상품 업로드
    public NestcoItems uploadItem(NestcoForm form, List<MultipartFile> images) throws IOException{
        log.info("업로드 아이템 처리 중...");
        LocalUserDetails currentUser = getCurrentUser();
        Member uploader = currentUser.getMemberEntity();  // LocalUserDetails에서 Member 객체 추출

        // 카테고리 정보 가져오기
        Category category = categoryService.getCategoryEntityById(form.getCategoryId());
        if (category == null) {
            throw new IllegalArgumentException("유효하지 않은 카테고리 ID입니다.");
        }
        log.info("카테고리: {}, 업로더: {}", category.getName(), uploader.getUsername());

        // 이미지 경로 처리 (파일 저장 후 경로 리스트 생성)
        List<String> imagePaths = new ArrayList<>();
        if (!images.isEmpty()) {
            for (MultipartFile file : images) {
                String imagePath = itemThumbnailService.saveImage(file);
                imagePaths.add(imagePath);
            }
        } else {
            throw new IllegalArgumentException("이미지 파일이 없습니다. 파일을 업로드해 주세요.");
        }

        // 상품 엔티티 생성
        NestcoItems newItem = NestcoItems.toUploadEntity(form, category, uploader, imagePaths);
        log.info("상품 엔티티 생성 완료. 이미지 경로 리스트: {}", imagePaths);

        // 상품 저장
        NestcoItems savedItem = nestcoItemsRepository.save(newItem);
        log.info("상품 저장 완료. 아이템 ID: {}", savedItem.getId());

        // 썸네일 저장
        itemThumbnailService.uploadThumbnail(savedItem, images);
        log.info("썸네일 업로드 완료.");

        return savedItem;
    }
    // 아이템 필터링 (Helper)
    public Page<NestcoItems> filterItems(String status, String location, Long categoryId, String sortOrder, String title, int page, int size) {
        QNestcoItems nestcoItems = QNestcoItems.nestcoItems;
        BooleanBuilder filter = new BooleanBuilder();

        if ((status == null || status.equals("all")) &&
                (location == null || location.equals("all")) &&
                categoryId == null &&
                (title == null || title.isEmpty())) {
            return getLatestItems(page, size);  // 최신 아이템 목록 반환
        }

        FilterHelper.addStatusFilter(filter, status, nestcoItems);
        FilterHelper.addLocationFilter(filter, location, nestcoItems);
        FilterHelper.addCategoryFilter(filter, categoryId, nestcoItems, categoryService);
        FilterHelper.addSearchFilter(filter, title, nestcoItems);

        OrderSpecifier<?> sortOrderSpecifier = FilterHelper.applySortOrder(nestcoItems, sortOrder);

        JPAQuery<NestcoItems> query = new JPAQuery<>(entityManager);
        query.from(nestcoItems)
                .where(filter)
                .orderBy(sortOrderSpecifier)
                .offset(page * size)
                .limit(size);

        List<NestcoItems> items = query.fetch();
        long total = query.fetchCount();

        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(items, pageable, total);
    }

    // 최신 상품 목록 조회
    public Page<NestcoItems> getLatestItems(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate"));
        return nestcoItemsRepository.findAllByOrderByCreateDateDesc(pageable);
    }

    // 카테고리별 상품 조회
    public Page<NestcoItems> getItemsByCategory(Long categoryId, int page, int size) {
        List<Long> categoryIds = categoryService.getAllSubCategoryIds(categoryId);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate"));
        return nestcoItemsRepository.findByCategoryIds(categoryIds, pageable);
    }

    // 검색어로 상품 조회
    public Page<NestcoItems> searchItemsByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate"));
        return nestcoItemsRepository.findByTitleContaining(title, pageable);  // Page 반환
    }

    // 카테고리와 검색어로 상품 조회
    public Page<NestcoItems> searchItemsByCategoryAndTitle(Long categoryId, String title, int page, int size) {
        List<Long> categoryIds = categoryService.getAllSubCategoryIds(categoryId);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate"));
        return nestcoItemsRepository.findByCategoryIdsAndTitle(categoryIds, title, pageable); // Page 반환
    }

    // 거래 완료되지 않은 상품 조회
    public List<NestcoItems> getAvailableItems() {
        return nestcoItemsRepository.findByStatusFalse();
    }

    // 상품 ID로 조회
    public NestcoItems getItemById(Long itemId) {
        return nestcoItemsRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }

    private LocalUserDetails getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof LocalUserDetails) {
            return (LocalUserDetails) principal;
        } else {
            throw new IllegalStateException("사용자가 인증되지 않았습니다.");
        }
    }

    // 조회수 저장
    public void saveHit(NestcoItems item) {
        nestcoItemsRepository.save(item);
    }

    // 아이템 개수
    public long getTotalItemsCount() {
        return nestcoItemsRepository.count();  // 전체 아이템 수
    }

    public List<NestcoItems> getItemsByUser(LocalUserDetails userDetails) {
        Member user = userDetails.getMemberEntity();  // LocalUserDetails에서 Member 객체를 추출
        return nestcoItemsRepository.findByUploader(user);
    }

}