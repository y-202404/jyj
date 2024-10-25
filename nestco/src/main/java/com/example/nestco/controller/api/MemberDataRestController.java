package com.example.nestco.controller.api;



import com.example.nestco.models.dto.MemberDTO;
import com.example.nestco.models.entity.Member;
import com.example.nestco.services.MemberDataRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberDataRestController {
    @Autowired
    private MemberDataRestService memberDataRestService;

    @PostMapping("/searchUserId")
    public ResponseEntity<Member> searchUserId (@RequestBody MemberDTO memberDTO) {
        ResponseEntity<Member> userInfo = memberDataRestService.findUserId(memberDTO.getUserId());
        return userInfo;

    }
}
