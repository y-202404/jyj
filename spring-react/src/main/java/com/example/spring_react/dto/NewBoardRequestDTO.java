package com.example.spring_react.dto;

import lombok.Data;

@Data
public class NewBoardRequestDTO {
    private MemberDTO memberDTO;
    private BoardDTO boardDTO;
}
