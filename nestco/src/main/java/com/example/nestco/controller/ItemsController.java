package com.example.nestco.controller;

import com.example.nestco.config.local.LocalUserDetails;
import com.example.nestco.dao.repository.MemberRepository;
import com.example.nestco.models.dto.CategoryDTO;
import com.example.nestco.models.dto.LikeRequestDTO;
import com.example.nestco.models.dto.LikeResponseDTO;
import com.example.nestco.models.dto.NestcoForm;
import com.example.nestco.models.entity.Category;
import com.example.nestco.models.entity.Member;
import com.example.nestco.models.entity.NestcoItems;
import com.example.nestco.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * 물품 등록, 조회, 카테고리별 아이템 조회 컨트롤러
 */

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemsController {

    private final NestcoItemsService nestcoItemsService;
    private final CategoryService categoryService;
    private final ItemThumbnailService itemThumbnailService;
    private final BaseService baseService;
    private final ItemLikeService itemLikeService;
    private final MemberService memberService;


    /**
     * 물품 등록 폼 이동 (로그인한 사용자만).
     */
    @GetMapping("/upload")
    public String showUploadForm(@AuthenticationPrincipal LocalUserDetails localUserDetails, Model model) {
        baseService.headerAttributes(model);
        if (localUserDetails != null) {
            List<CategoryDTO> parentCategories = categoryService.getTopCategories(); // 대분류 카테고리만 가져오기
            model.addAttribute("parentCategories", parentCategories);
            model.addAttribute("nestcoForm", new NestcoForm());
            return "logined/uploadForm";
        }
        return "redirect:/loginForm";
    }

    // 물품 업로드 처리
    @PostMapping("/upload")
    public String uploadItem(@ModelAttribute NestcoForm form,
                             @AuthenticationPrincipal LocalUserDetails localUserDetails,
                             @RequestParam("images") List<MultipartFile> imageFiles,
                             Model model) throws IOException {

        // 파일 형식 제한
        String[] allowedTypes = {"image/jpeg", "image/png", "image/gif"};

        // 파일 크기 제한 (단일 파일 5MB 제한)
        long maxFileSize = 5 * 1024 * 1024;

        // 이미지 파일 예외처리
        try {
            for (MultipartFile file : imageFiles) {
                // 파일이 비어있지 않은지 확인
                if (file.isEmpty()) {
                    throw new IllegalArgumentException("파일을 선택해주세요.");
                }

                // 파일 형식이 허용되는지 확인
                String fileType = file.getContentType();
                if (!List.of(allowedTypes).contains(fileType)) {
                    throw new IllegalArgumentException("지원하지 않는 파일 형식입니다: " + fileType);
                }

                // 파일 크기 확인
                if (file.getSize() > maxFileSize) {
                    throw new IllegalArgumentException("파일 크기는 최대 5MB까지 허용됩니다.");
                }
            }

            nestcoItemsService.uploadItem(form, imageFiles);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("parentCategories", categoryService.getTopCategories());
            return "logined/uploadForm";  // 에러 발생 시 다시 업로드 페이지로 이동
        } catch (IOException e) {
            model.addAttribute("errorMessage", "파일 업로드 중 오류가 발생했습니다.");
            model.addAttribute("parentCategories", categoryService.getTopCategories());
            return "logined/uploadForm";  // 에러 발생 시 다시 업로드 페이지로 이동
        }
        return "redirect:/nestco";
    }

    /**
     * 물품 상세 정보 조회.
     */
    @GetMapping("/{id}")
    public String viewItemDetail(@PathVariable("id") Long itemId, Model model) {
        NestcoItems item = nestcoItemsService.getItemById(itemId);

        // 공통화 메서드(Header)
        baseService.headerAttributes(model);

        // 조회수 증가
        item.incrementHits();
        nestcoItemsService.saveHit(item);

        // 등록된 후 경과 시간 계산
        String TimeSincePosted = item.getTimeSincePosted();
        model.addAttribute("TimeSincePosted", TimeSincePosted);
        model.addAttribute("item", item);

        // 아이템에 등록된 모든 썸네일 이미지 가져오기
        List<String> imagePaths = itemThumbnailService.getAllThumbnailsForItem(itemId);
        List<Map<String, Object>> imagesWithFlag = new ArrayList<>();

        for (int i = 0; i < imagePaths.size(); i++) {
            Map<String, Object> imageMap = new HashMap<>();
            imageMap.put("imagePath", imagePaths.get(i));
            imageMap.put("isFirst", i == 0);  // 첫 번째 이미지는 true로 설정
            imagesWithFlag.add(imageMap);
        }

        model.addAttribute("imagePaths", imagesWithFlag);
        return "/nestcoes/itemDetail";
    }

    @PostMapping("/like/{itemId}")
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> itemLike(@PathVariable Long itemId, @AuthenticationPrincipal LocalUserDetails localUserDetails) {
        HashMap<String, Object> responseMap = new HashMap<>();

        if (localUserDetails == null) {
            responseMap.put("success", false);
            responseMap.put("message", "로그인이 필요합니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMap);
        }
        String userId = localUserDetails.getMemberEntity().getUserId();
        log.info("userId : " + userId);
        log.info("itemId : " + itemId);

        try {
            // 찜 추가 처리
            LikeRequestDTO likeRequestDTO = new LikeRequestDTO(userId, itemId);
            LikeResponseDTO likeResponseDTO = itemLikeService.saveItemLike(likeRequestDTO);

            // 성공적으로 찜한 경우 응답 데이터 설정
            responseMap.put("success", true);
            responseMap.put("likeId", likeResponseDTO.getLikeId());
            responseMap.put("message", likeResponseDTO.getMessage());

        } catch (IllegalStateException e) {
            // 이미 찜한 아이템인 경우 예외 처리
            responseMap.put("success", false);
            responseMap.put("message", e.getMessage());
        } catch (Exception e) {
            // 기타 예외 처리
            responseMap.put("success", false);
            responseMap.put("message", "찜 기능 처리 중 오류가 발생했습니다.");
        }

        return ResponseEntity.ok(responseMap);
    }




    /**
     * 카테고리별 물품 목록 조회.
     */

    // 대분류 카테고리 가져오기
    @GetMapping("/top")
    @ResponseBody
    public List<CategoryDTO> getTopCategories() {
        return categoryService.getTopCategories();
    }

    // 중분류 카테고리 가져오기 (대분류 선택 시)
    @GetMapping("/middle/{parentId}")
    @ResponseBody
    public List<CategoryDTO> getMiddleCategories(@PathVariable Long parentId) {
        return categoryService.getMiddleCategories(parentId);
    }

    // 소분류 카테고리 가져오기 (중분류 선택 시)
    @GetMapping("/child/{parentId}")
    @ResponseBody
    public List<CategoryDTO> getChildCategories(@PathVariable Long parentId) {
        return categoryService.getChildCategories(parentId);
    }
}


