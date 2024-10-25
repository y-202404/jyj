package com.example.nestco.controller.api;

import com.example.nestco.models.dto.MemberDTO;
import com.example.nestco.services.LoginRestService;
import com.example.nestco.services.SocialLoginRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocialLoginRestController {
    @Autowired
    private LoginRestService loginRestService;

    @Autowired
    private SocialLoginRestService socialLoginRestService;

    @PostMapping("/join/social")
    public ResponseEntity<String> join(@RequestBody MemberDTO memberDTO) {
        ResponseEntity<String> responsedValue = socialLoginRestService.checkMember(memberDTO);

        if (responsedValue.getBody() == null) {
            if (loginRestService.save(memberDTO) == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("회원가입 시도를 실패하였습니다");
            }
        }
        return responsedValue;
    }
}