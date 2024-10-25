package com.example.nestco.services;

import com.example.nestco.dao.repository.MemberRepository;
import com.example.nestco.models.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SocialLoginRestService {
    @Autowired
    private MemberRepository memberRepository;

    public ResponseEntity<String> checkMember(MemberDTO memberDTO){
        String userId = memberDTO.getUserId();
        String nickName = memberDTO.getNickname();
        String phoneNumber = memberDTO.getPhoneNumber();
        String email = memberDTO.getEmail();

        if(memberRepository.findByUserId(userId) != null) {
//            System.out.println("동일아이디");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("동일한 아이디가 존재합니다. 아이디를 확인해 주세요.");
        } else if (memberRepository.findByNickname(nickName) != null) {
//            System.out.println("동일 닉네임");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("동일한 닉네임의 계정이 존재합니다. 닉네임을 확인해 주세요.");
        } else if (memberRepository.findByPhoneNumber(phoneNumber) != null) {
//            System.out.println("동일 폰번호");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("동일한 휴대폰번호로 가입된 계정이 존재합니다. 아이디 또는 비밀번호찾기를 이용하여 계정을 찾을 수 있씁니다.");
        } else if (memberRepository.findByEmail(email) != null) {
//            System.out.println("동일 이메일");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("동일한 이메일로 가입된 계정이 존재합니다. 아이디 또는 비밀번호찾기를 이용하여 계정을 찾을 수 있습니다.");
        }

        System.out.println("중복 문제없음");
        return ResponseEntity.ok(null);
    }
}
