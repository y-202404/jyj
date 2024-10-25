package com.example.spring_react.dto;

import com.example.spring_react.entity.Member;
import com.example.spring_react.entity.UserEvent;
import lombok.Data;

@Data
public class UserEventDTO {
    private Long id;
    private Member memberId;
    private String eventJoin;
    private String result;

    public UserEvent createUserEvent(UserEventDTO userEventDTO) {
        return new UserEvent(id, memberId, eventJoin, result);
    }
}
