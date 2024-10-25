package com.example.nestco.controller.api;

import com.example.nestco.dao.repository.MemberRepository;
import com.example.nestco.models.dto.MemberDTO;
import com.example.nestco.models.dto.NicknameDTO;
import com.example.nestco.models.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class itemsRestController {

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping("/items/check/nickname")
    public ResponseEntity<String> checkNickname(@RequestBody NicknameDTO nicknameDTO) {
        String nickname = nicknameDTO.getNickname();

        System.out.println(nickname);

        MemberDTO memberDTO = new MemberDTO();

        Member member = memberRepository.findByNickname(nickname);


        memberDTO.setDeclaration(member.getDeclaration() + 1);

        Member memberupdate = memberDTO.createMemberEntity(memberDTO);

        member.update(memberupdate);

        memberRepository.save(member);

        return ResponseEntity.ok("신고 성공");
    }
}
