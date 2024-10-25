package com.example.nestco.models.dto;

import com.example.nestco.models.entity.Board;
import com.example.nestco.models.entity.Member;
import lombok.Data;

@Data
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
