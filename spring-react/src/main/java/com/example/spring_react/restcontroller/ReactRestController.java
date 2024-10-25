package com.example.spring_react.restcontroller;

import com.example.spring_react.dto.LoginResponseDTO;
import com.example.spring_react.service.ReactRestService;
import com.example.spring_react.dto.MemberDTO;
import com.example.spring_react.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReactRestController {

    @Autowired
    private ReactRestService reactRestService;

    // 로그인 처리
    @PostMapping("/react/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody MemberDTO memberDTO) {
        String userId = memberDTO.getUserId();
        String userPassword = memberDTO.getUserPassword();

        Member member = reactRestService.getMemberList(userId, userPassword);

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();

        if(member.getUserId() == null) {
            loginResponseDTO.setMember(member);
            loginResponseDTO.setMessage("아이디 또는 비밀번호가 틀렸습니다.");
            return ResponseEntity.ok(loginResponseDTO);
        }
        if(member.getBlock().equals("1")) {
            loginResponseDTO.setMember(member);
            loginResponseDTO.setMessage("차단된 사용자입니다.");
            return ResponseEntity.ok(loginResponseDTO);
        }

        loginResponseDTO.setMember(member);
        loginResponseDTO.setMessage("로그인 성공");

        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("react/join")
    public ResponseEntity<String> reactJoin(@RequestBody MemberDTO memberDTO) {
        System.out.println(memberDTO.getAddress());

        String result = reactRestService.memberCheck(memberDTO);

        if(result == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        return ResponseEntity.ok(result);
    }
}
