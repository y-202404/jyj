package com.example.nestco.controller;


import com.example.nestco.dao.repository.MemberRepository;
import com.example.nestco.models.dto.MailDTO;
import com.example.nestco.models.entity.Member;
import com.example.nestco.services.MailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;
    private final MemberRepository memberRepository;

    @ResponseBody
    @PostMapping("/emailCheck")
    public ResponseEntity<String> emailCheck(@RequestBody MailDTO mailDTO, HttpSession session)
            throws MessagingException, UnsupportedEncodingException {

        String email = mailDTO.getEmail();

        List<Member> memberList = memberRepository.findAll();

        Member member = new Member();

        for(Member item : memberList) {
            if(item.getEmail().equals(email)) {
                member = item;
            }
        }

        if(member.getEmail() == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("에러");
        }

        String authCode = mailService.sendSimpleMessage(mailDTO.getEmail(), session);
        return ResponseEntity.ok(authCode); // Response body에 값을 반환
    }
}
