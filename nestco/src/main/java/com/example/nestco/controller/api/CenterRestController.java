package com.example.nestco.controller.api;

import com.example.nestco.config.local.LocalUserDetails;
import com.example.nestco.models.entity.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CenterRestController {

    // 로그인 처리 로직
    @GetMapping("/center/checkLogin")
    public ResponseEntity<String> checkLogin(@AuthenticationPrincipal LocalUserDetails localUserDetails) {
        Member member = localUserDetails.getMemberEntity();

        if(member == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("로그인 후 사용하실 수 있습니다.");
        }

        return ResponseEntity.ok("로그인이 되어 있습니다.");
    }
}
