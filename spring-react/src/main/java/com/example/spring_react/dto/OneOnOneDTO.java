package com.example.spring_react.dto;

import com.example.spring_react.entity.Member;
import com.example.spring_react.entity.OneOnOne;
import lombok.Data;

@Data
public class OneOnOneDTO {
    private Long id;
    private String type;
    private Member memberId;
    private String phoneNumber;
    private String content;

    public OneOnOne toEntity(OneOnOneDTO oneOnOneDTO) {
        return new OneOnOne(id, type, memberId, phoneNumber, content);
    }

}
