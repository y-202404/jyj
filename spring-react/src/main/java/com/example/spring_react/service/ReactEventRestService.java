package com.example.spring_react.service;

import com.example.spring_react.dto.EventRequestDTO;
import com.example.spring_react.dto.MemberDTO;
import com.example.spring_react.dto.UserEventDTO;
import com.example.spring_react.entity.Member;
import com.example.spring_react.entity.UserEvent;
import com.example.spring_react.repository.UserEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ReactEventRestService {


    @Autowired
    private UserEventRepository userEventRepository;


    public String checkUser(EventRequestDTO eventRequestDTO) {

        MemberDTO memberDTO = eventRequestDTO.getMemberDTO();

        Member member = memberDTO.createMemberEntity(memberDTO);

        UserEvent userEvent = userEventRepository.findByMemberId(member);

        if(userEvent == null) {
            Random random = new Random();
            int resultRandom = random.nextInt(2);
            if(resultRandom == 0) {
                UserEventDTO userEventDTO = new UserEventDTO();
                userEventDTO.setEventJoin("참여");
                userEventDTO.setResult("꽝");
                userEventDTO.setMemberId(member);
                UserEvent target = userEventDTO.createUserEvent(userEventDTO);
                userEventRepository.save(target);
                return "이벤트 참여에 성공하였습니다.";
            } else {
                UserEventDTO userEventDTO = new UserEventDTO();
                userEventDTO.setEventJoin("참여");
                userEventDTO.setResult("당첨");
                userEventDTO.setMemberId(member);
                UserEvent target = userEventDTO.createUserEvent(userEventDTO);
                userEventRepository.save(target);
                return "이벤트 참여에 성공하였습니다.";
            }
        }

        if(userEvent.getEventJoin().equals("참여")) {
            return "이미 이벤트에 참여하셨습니다.";
        }

        return null;
    }
}
