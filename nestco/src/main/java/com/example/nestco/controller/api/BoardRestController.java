package com.example.nestco.controller.api;

import com.example.nestco.config.local.LocalUserDetails;
import com.example.nestco.dao.repository.BoardRepository;
import com.example.nestco.models.dto.NicknameDTO;
import com.example.nestco.models.entity.Board;
import com.example.nestco.models.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class BoardRestController {

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("/board/check/userId")
    public ResponseEntity<String> checkUserId(@AuthenticationPrincipal LocalUserDetails localUserDetails) {
        Member member = localUserDetails.getMemberEntity();
        if(member == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("등록되지않음");
        }

        return ResponseEntity.ok("등록됌");
    }

    @PostMapping("/board/check/nickname")
    public ResponseEntity<String> checkNickname(@RequestBody NicknameDTO nicknameDTO, @AuthenticationPrincipal LocalUserDetails localUserDetails) {
        Member member = localUserDetails.getMemberEntity();

        String nickname = nicknameDTO.getNickname();

        if(!nickname.equals(member.getNickname())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("에러");
        }

        return ResponseEntity.ok("통과");
    }

    @PostMapping("/board/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        Board board = boardRepository.findById(id).orElse(null);

        boardRepository.delete(board);

        return ResponseEntity.ok("통과");
    }
}
