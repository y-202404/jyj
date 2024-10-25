package com.example.spring_react.dto;

import com.example.spring_react.entity.Member;
import lombok.Data;

@Data
public class LoginResponseDTO {
    private Member member;
    private String message;
}
