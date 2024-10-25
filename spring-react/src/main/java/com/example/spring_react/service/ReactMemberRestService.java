package com.example.spring_react.service;

import com.example.spring_react.dto.MemberDTO;
import com.example.spring_react.entity.Member;
import com.example.spring_react.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReactMemberRestService {

    @Autowired
    private MemberRepository memberRepository;

    public Page<Member> memberPage(Pageable pageable) {
        Page<Member> memberPage = memberRepository.findAll(pageable);

        return memberPage;
    }

    public Page<Member> searchMemberPage(Pageable pageable, String searchKey) {
        Page<Member> memberPage = memberRepository.findAllByQuery(pageable, searchKey);

        return memberPage;
    }

    public Member memberBlock(MemberDTO memberDTO) {
        Member member = memberRepository.findByUserId(memberDTO.getUserId());

        if(member.getBlock().equals("0")) {
            MemberDTO memberDTOClone = new MemberDTO();
            memberDTOClone.setBlock("1");

            Member target = memberDTOClone.createMemberEntity(memberDTOClone);
            member.update(target);

            memberRepository.save(member);

            return member;
        }

        MemberDTO memberDTOClone = new MemberDTO();
        memberDTOClone.setBlock("0");

        Member target = memberDTOClone.createMemberEntity(memberDTOClone);
        member.update(target);

        memberRepository.save(member);

        return member;
    }
}
