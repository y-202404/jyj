package com.example.nestco.controller.api;

import com.example.nestco.config.local.LocalUserDetails;
import com.example.nestco.dao.repository.UserEventRepository;
import com.example.nestco.models.dto.UserEventDTO;
import com.example.nestco.models.entity.Member;
import com.example.nestco.models.entity.UserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class UserEventRestController {

    @Autowired
    private UserEventRepository userEventRepository;

    @GetMapping("/event/check")
    public ResponseEntity<String> check(@AuthenticationPrincipal LocalUserDetails localUserDetails) {

        if(localUserDetails == null) {
            return ResponseEntity.ok("로그인을 하셔야 이벤트에 참여할 수 있습니다.");
        }

        Member member = localUserDetails.getMemberEntity();

        UserEvent userEvent = userEventRepository.findByMemberId(member);

        if(userEvent != null) {
            if(userEvent.getEventJoin().equals("참여")) {
                return ResponseEntity.ok("이미 이벤트에 참여하셨습니다.");
            }
        }

        UserEventDTO userEventDTO = new UserEventDTO();
        userEventDTO.setMemberId(member);
        userEventDTO.setEventJoin("참여");

         //로직 추가
        Random random = new Random();
        int randomResult = random.nextInt(2); // 0 또는 1이 나옴

        String result = null;

        if(randomResult == 0) {
            result = "당첨";
        } else {
            result = "꽝";
        }

        userEventDTO.setResult(result);
//         랜덤으로 돌려서 dto에 해당 값 세팅
//         int randomresult = random(0또는 1이 나옴)
//         String result = "";
//        if(randomresult == 0) {
//            result = "당첨";
//        } else {
//            result = "꽝"
//        }
//        userEventDTO.setResult(result)
//        userEventDTO.setResult("당첨");  // 날리기전에 확인

        UserEvent target = userEventDTO.createUserEvent(userEventDTO);



        userEventRepository.save(target);

        return ResponseEntity.ok("이벤트 참여에 성공하셨습니다.");
    }
}
