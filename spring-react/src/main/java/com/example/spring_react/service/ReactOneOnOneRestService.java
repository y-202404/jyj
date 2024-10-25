package com.example.spring_react.service;

import com.example.spring_react.dto.MemberDTO;
import com.example.spring_react.dto.OneOnOneDTO;
import com.example.spring_react.dto.OneOnOneRequestDTO;
import com.example.spring_react.entity.Member;
import com.example.spring_react.entity.OneOnOne;
import com.example.spring_react.repository.OneOnOneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReactOneOnOneRestService {

    @Autowired
    private OneOnOneRepository oneOnOneRepository;

    public OneOnOne addOne(OneOnOneRequestDTO oneOnOneRequestDTO) {
        MemberDTO memberDTO = oneOnOneRequestDTO.getMemberDTO();
        OneOnOneDTO oneOnOneDTO = oneOnOneRequestDTO.getOneOnOneDTO();

        Member member = memberDTO.createMemberEntity(memberDTO);

        oneOnOneDTO.setMemberId(member);

        OneOnOne oneOnOne = oneOnOneDTO.toEntity(oneOnOneDTO);

        oneOnOneRepository.save(oneOnOne);

        return oneOnOne;
    }
}
