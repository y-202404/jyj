package com.example.spring_react.dto;

import com.example.spring_react.entity.Member;
import lombok.Data;

@Data
public class SearchNestcoItemsDTO {
    private String sortOrder;
    private String status;
    private String location;
    private Member member;
}
