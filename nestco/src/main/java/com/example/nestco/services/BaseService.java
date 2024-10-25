package com.example.nestco.services;

import com.example.nestco.config.local.LocalUserDetails;
import com.example.nestco.models.dto.CategoryDTO;
import com.example.nestco.models.entity.NestcoItems;
import com.example.nestco.models.entity.QNestcoItems;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class BaseService {

    private final CategoryService categoryService;

    @PersistenceContext
    private EntityManager entityManager;

    public BaseService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    public void headerAttributes(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String nickname = null;
        String address = null;

        if (principal instanceof LocalUserDetails) {
            LocalUserDetails userDetails = (LocalUserDetails) principal;
            nickname = userDetails.getMemberEntity().getNickname();
            address = userDetails.getMemberEntity().getAddress();
        }

        // 카테고리 목록 가져오기
        List<CategoryDTO> categories = categoryService.getCategoryTree(null);

        // 모델에 사용자 정보와 카테고리 정보 추가
        model.addAttribute("nickname", nickname);
        model.addAttribute("address", address);
        model.addAttribute("categories", categories);
    }


    /**
     * 공통 카테고리 필터링 기능
     */
    public BooleanBuilder categoryFilter(Long categoryId) {
        if (categoryId == null) {
            return null;
        }

        BooleanBuilder builder = new BooleanBuilder();

        // 선택된 카테고리와 그 하위 카테고리 모두 가져오기
        List<Long> subCategoryIds = categoryService.getAllSubCategoryIds(categoryId); // 하위 카테고리 포함

        if (!subCategoryIds.isEmpty()) {
            builder.and(QNestcoItems.nestcoItems.category.id.in(subCategoryIds));
        }

        return builder;
    }

    /**
     * 공통 검색 필터링 기능
     */
    public BooleanBuilder searchFilter(String searchQuery) {
        if (searchQuery == null || searchQuery.isEmpty()) {
            return null;
        }

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QNestcoItems.nestcoItems.title.containsIgnoreCase(searchQuery));

        return builder;
    }

    /**
     * 카테고리와 검색 필터 적용 및 페이지 조회
     */
    public List<NestcoItems> filterItemsWithCategoryAndSearch(String searchQuery, Long categoryId) {
        BooleanBuilder filter = new BooleanBuilder();

        // 카테고리 필터 적용
        filter.and(categoryFilter(categoryId));

        // 검색 필터 적용
        filter.and(searchFilter(searchQuery));

        JPAQuery<NestcoItems> query = new JPAQuery<>(entityManager);
        query.from(QNestcoItems.nestcoItems)
                .where(filter);

        return query.fetch();
    }

}
