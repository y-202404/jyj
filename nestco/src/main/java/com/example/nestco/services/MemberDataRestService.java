package com.example.nestco.services;

import com.example.nestco.dao.repository.MemberRepository;
import com.example.nestco.models.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MemberDataRestService {
    @Autowired
    private MemberRepository memberRepository;

    public ResponseEntity<Member> findUserId(String userId) {
        Member userInfo = memberRepository.findByUserId(userId);
        if(userInfo == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        return ResponseEntity.ok(userInfo);
    }

}
