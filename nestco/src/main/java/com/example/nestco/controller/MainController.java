package com.example.nestco.controller;

import com.example.nestco.config.local.LocalUserDetails;
import com.example.nestco.models.dto.CategoryDTO;
import com.example.nestco.models.entity.ItemThumbnail;
import com.example.nestco.services.*;
import com.example.nestco.models.entity.NestcoItems;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final NestcoItemsService nestcoItemsService;
    private final BaseService baseService;
    private final CategoryService categoryService;
    private final ItemThumbnailService itemThumbnailService;

    /**
     * 비로그인 유저도 사용 가능한 기능 (카테고리별, 검색 조회)
     */
    @GetMapping("/nestco")
    public String showHomePage(@RequestParam(name = "categoryId", required = false) Long categoryId,
                               @RequestParam(name = "searchQuery", required = false) String searchQuery,
                               @RequestParam(name = "sortOrder", required = false) String sortOrder,
                               @RequestParam(name = "status", required = false) String status,
                               @RequestParam(name = "location", required = false) String location,
                               @RequestParam(name = "page", defaultValue = "0") int page,
                               @RequestParam(name = "size", defaultValue = "10") int size,
                               Model model,
                               @AuthenticationPrincipal LocalUserDetails localUserDetails) {

        baseService.headerAttributes(model);
//        model.addAttribute("nickname", localUserDetails != null ? localUserDetails.getMemberEntity().getNickname() : null);
//        model.addAttribute("address", localUserDetails != null ? localUserDetails.getMemberEntity().getAddress() : null);
        Page<NestcoItems> itemsPage = nestcoItemsService.filterItems(status, location, categoryId, sortOrder, searchQuery, page, size);
        List<NestcoItems> items = itemsPage.getContent();

        // 썸네일 및 아이템 처리
        List<Long> itemIds = items.stream().map(NestcoItems::getId).collect(Collectors.toList());
        Map<Long, String> thumbnailMap = itemThumbnailService.getFirstThumbnailsForItems(itemIds);

        // 아이템에 썸네일 경로 설정
        for (NestcoItems item : items) {
            String thumbnailPath = thumbnailMap.get(item.getId());
            if (thumbnailPath != null) {
                // 썸네일 경로가 있을 경우 설정
                item.setImagePath(thumbnailPath);
            } else {
                // 썸네일 경로가 없을 경우 기본 이미지 설정
                item.setImagePath("/images/default-thumbnail.svg");
            }

            // 등록 시간 계산 (getTimeSincePosted 메서드 사용)
            String timeSincePosted = item.getTimeSincePosted();
            model.addAttribute("timeSincePosted" + item.getId(), timeSincePosted);
        }

        model.addAttribute("items", items);
        model.addAttribute("currentPage", itemsPage.getNumber());  // 현재 페이지
        model.addAttribute("totalPages", itemsPage.getTotalPages());
        return "nestcoes/home";  // home.mustache와 연결
    }

    // 하위 카테고리 불러오기
    @GetMapping("/categories/subcategories")
    @ResponseBody
    public List<CategoryDTO> getSubCategories(@RequestParam Long parentId) {
        return categoryService.getSubCategories(parentId);
    }


    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        SecurityContextHolder.clearContext();
        return "redirect:/nestco";  // 로그아웃 처리 후 로그인 페이지로 리다이렉트
    }
}

//
//    @GetMapping
//    public String showMyPage(@AuthenticationPrincipal Member member, Model model) {
//        model.addAttribute("member", member);
//        model.addAttribute("uploadedItems", memberService.getUploadedItems(member));
//        return "mypage";  // mypage.mustache와 연결
//    }









