package com.example.spring_react.restcontroller;

import com.example.spring_react.dto.NoticeDTO;
import com.example.spring_react.entity.NoticeBoard;
import com.example.spring_react.service.ReactNoticeRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReactNoticeRestController {

    @Autowired
    private ReactNoticeRestService reactNoticeRestService;

    @GetMapping("/noticeData")
    public ResponseEntity<Page<NoticeBoard>> noticeData(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)Pageable pageable,
                                                        @RequestParam(name = "searchKey", required = false) String searchKey) {

        if(searchKey == null) {
            Page<NoticeBoard> noticePage = reactNoticeRestService.createNoticePage(pageable);

            if(noticePage == null) {
                ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
            return ResponseEntity.ok(noticePage);
        }

        Page<NoticeBoard> noticePage = reactNoticeRestService.createSearchNoticePage(pageable, searchKey);

        if(noticePage == null) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        return ResponseEntity.ok(noticePage);
    }

    @PostMapping("/noticeDelete")
    public ResponseEntity<String> noticeDelete(@RequestBody NoticeDTO noticeDTO) {
        NoticeBoard noticeBoard = reactNoticeRestService.deleteNotice(noticeDTO);

        if(noticeBoard == null) {
            ResponseEntity.status(HttpStatus.CONFLICT).body("데이터 조회 실패");
        }

        return ResponseEntity.ok("데이터 삭제 성공");
    }

    @PostMapping("/noticeData/readCount")
    public ResponseEntity<String> noticeReadCountAdd(@RequestBody NoticeDTO noticeDTO) {

        System.out.println(noticeDTO.getId());

        NoticeBoard noticeBoard = reactNoticeRestService.addReadCount(noticeDTO);

        if(noticeBoard == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("에러");
        }

        return ResponseEntity.ok("조회수 증가");
    }
}
