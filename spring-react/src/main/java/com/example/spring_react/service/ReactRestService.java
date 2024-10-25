package com.example.spring_react.service;

import com.example.spring_react.dto.MemberDTO;
import com.example.spring_react.repository.MemberRepository;
import com.example.spring_react.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReactRestService {

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private MemberRepository memberRepository;

    public Member getMemberList(String userId, String userPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Member searchMember = new Member();
        Member member = memberRepository.findByUserId(userId);

        if(encoder.matches(userPassword, member.getUserPassword())) {
            searchMember = member;
        }

        return searchMember;
    }

    public String memberCheck(MemberDTO memberDTO) {
        List<Member> memberList = memberRepository.findAll();

        Member member = memberDTO.createMemberEntity(memberDTO);

        for(Member item : memberList) {
            if(item.getUserId().equals(member.getUserId())) {
                return "중복된 아이디입니다. 다른 아이디를 입력해주십시오.";
            }
            if(item.getEmail().equals(member.getEmail())) {
                return "중복된 이메일입니다. 다른 이메일을 입력해주십시오.";
            }
            if(item.getNickname().equals(member.getNickname())) {
                return "중복된 닉네임입니다. 다른 닉네임을 입력해주십시오.";
            }
            if(item.getPhoneNumber().equals(member.getPhoneNumber())) {
                return "중복된 휴대폰 번호입니다. 다른 휴대폰 번호를 입력해주십시오.";
            }
        }

        String prevPassword = member.getUserPassword();

        String newPassword = bCryptPasswordEncoder.encode(prevPassword);

        memberDTO.setUserPassword(newPassword);

        Member target = memberDTO.createMemberEntity(memberDTO);

        memberRepository.save(target);

        return "회원가입에 성공하였습니다.";
    }
}
