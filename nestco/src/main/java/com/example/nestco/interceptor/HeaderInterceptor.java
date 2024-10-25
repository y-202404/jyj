//package com.example.nestco.interceptor;
//
//import com.example.nestco.config.local.LocalUserDetails;
//import com.example.nestco.models.dto.CategoryDTO;
//import com.example.nestco.services.CategoryService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import java.util.List;
//
///**
// *  세션체크 숫자를 줄여 코드 누락 위험 낮추기
// *  메서드 : preHandle, postHandle, afterCompletion
// */
//@Component
//public class HeaderInterceptor implements HandlerInterceptor {
//
//    private final CategoryService categoryService;
//
//    public HeaderInterceptor(CategoryService categoryService) {
//        this.categoryService = categoryService;
//    }
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("Interceptor가 호출되었습니다.");
//        // 로그인 유저 정보 가져오기
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String nickname = null;
//        String address = null;
//
//        // 로그인된 경우, LocalUserDetails에서 정보를 가져온다.
//        if (principal instanceof LocalUserDetails) {
//            LocalUserDetails userDetails = (LocalUserDetails) principal;
//            nickname = userDetails.getMemberEntity().getNickname();
//            address = userDetails.getMemberEntity().getAddress();
//        }
//
//        // 카테고리 정보 가져오기
//        List<CategoryDTO> categories = categoryService.getCategoryTree(null);
//
//        // request에 공통 데이터를 설정 (이 데이터는 모든 View에서 접근 가능)
//        request.setAttribute("nickname", nickname);
//        request.setAttribute("address", address);
//        request.setAttribute("categories", categories);
//
//        return true;  // 다음 단계(컨트롤러 실행)로 진행
//    }
//}
