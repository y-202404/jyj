package com.example.nestco.models.dto;

import com.example.nestco.models.entity.Member;
import com.example.nestco.models.entity.UserEvent;
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
