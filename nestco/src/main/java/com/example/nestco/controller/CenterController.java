package com.example.nestco.controller;

import com.example.nestco.config.local.LocalUserDetails;
import com.example.nestco.dao.repository.NoticeBoardRepository;
import com.example.nestco.dao.repository.OneOnOneRepository;
import com.example.nestco.models.dto.OneOnOneForm;
import com.example.nestco.models.entity.Member;
import com.example.nestco.models.entity.NoticeBoard;
import com.example.nestco.models.entity.OneOnOne;
import com.example.nestco.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class CenterController {
    @Autowired
    private OneOnOneRepository oneOnOneRepository;

    @Autowired
    private NoticeBoardRepository noticeBoardRepository;

    @Autowired
    private BaseService baseService;

    private  final Random random = new Random();

    //주소 고객센터페이지
    @GetMapping("/nestco/centerpage")
    public String centers(Model model) {
        baseService.headerAttributes(model);
        return "center/centerPage";     // 위치
    }

    // 1:1 문의 페이지로 이동
    @GetMapping("/nestco/oneonone")
    public String oneOnOnes(Model model) {
         baseService.headerAttributes(model);
        return "center/oneOnOne"; // Mustache 템플릿을 반환
}

    // 1:1 문의 폼 생성
    @PostMapping("/nestco/oneOneOnCreate")
    public String createOneOnOne(@AuthenticationPrincipal LocalUserDetails localUserDetails, OneOnOneForm form){ //1:1문의 폼에 파라미터 폼을 선언
        Member member = localUserDetails.getMemberEntity();
        //1. 디티오를 엔티티로
        form.setMemberId(member);
        form.setPhoneNumber(member.getPhoneNumber());
        OneOnOne oneOnOne = form.toEntity(form);
        //2. 래파지토리에게 엔티티를 디비에 저장하게 함
        OneOnOne saved = oneOnOneRepository.save(oneOnOne);

        return "redirect:/nestco/centerpage"; //저장 후 상세페이지로 리다이렉트 //문제수정
    }

    //1:1 문의 상세페이지
    @GetMapping("oneOnOne/{id}")
    public String show(@PathVariable Long id, Model model) {
        //1. 아이디로 데이터를 가져옴
         OneOnOne oneOnOneEntity = oneOnOneRepository.findById(id).orElse(null);

        //2. 가져온 데이터를 모델에 등록
        model.addAttribute("oneOnOne", oneOnOneEntity);

        //3. 보여줄 페이지를 설정
        return "center/oneOnOneBoard";
    }

    //공지사항 목록 페이지
    @GetMapping("/nestco/noticeboard")
    public String noticeBoards(Model model,
                               @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                               @RequestParam(value = "page", defaultValue = "1") int page) {
        baseService.headerAttributes(model);

        Pageable newPageable = PageRequest.of(page - 1, pageable.getPageSize(), pageable.getSort());
        Page<NoticeBoard> noticeBoardList = noticeBoardRepository.findAll(newPageable);

        int totalPages = noticeBoardList.getTotalPages();
        int currentPage = newPageable.getPageNumber() + 1;

        int start = Math.max(1, currentPage - 2);
        int end = Math.min(totalPages, currentPage + 2);

        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            pageNumbers.add(i);
        }

        int firstPage = 1;
        int lastPage = totalPages;

        model.addAttribute("notices", noticeBoardList);
        model.addAttribute("prev", Math.max(1, currentPage - 1));
        model.addAttribute("next", Math.min(totalPages, currentPage + 1));
        model.addAttribute("hasNext", noticeBoardList.hasNext());
        model.addAttribute("hasPrev", noticeBoardList.hasPrevious());
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("firstPage", firstPage);
        model.addAttribute("lastPage", lastPage);

        return "center/noticeBoard";
    }

    // 공지사항 상세 페이지
    @GetMapping("/nestco/noticeboard/{id}")
    public String noticeDetail(@PathVariable("id") Long id, Model model) {
        NoticeBoard notice = noticeBoardRepository.findById(id).orElse(null);

        if (notice == null) {
            return "redirect:/nestco/noticeboard";
        }

        // 조회수 증가
        notice.incrementReadCount();
        noticeBoardRepository.save(notice);

        model.addAttribute("notice", notice);
        return "center/noticeDetail";
    }
    
    // 이벤트 페이지
    @GetMapping("/nestco/event")
    public String events(Model model) {
        baseService.headerAttributes(model);
        return "center/event";
    }
}

