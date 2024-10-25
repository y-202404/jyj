package com.example.spring_react.dto;

import com.example.spring_react.entity.Board;
import com.example.spring_react.entity.Member;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BoardDTO {
    private Long id;
    private String title;
    private String content;
    private Member member;
    private String create;
    private Integer readCount;

    public Board createBoard(BoardDTO boardDTO) {
        return new Board(id, title, content, member, create, readCount);
    }
}
