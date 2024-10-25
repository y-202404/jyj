package com.example.spring_react.dto;

import lombok.Data;

@Data
public class EditBoardRequestDTO {
    private BoardDTO boardDTO;
    private SubBoardDTO subBoardDTO;
}
