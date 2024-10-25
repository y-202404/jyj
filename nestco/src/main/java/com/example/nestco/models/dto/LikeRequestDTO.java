package com.example.nestco.models.dto;

import lombok.Data;

@Data
public class LikeRequestDTO {
    private Long itemId;    // 찜할 아이템의 Id
    private String userId;    // 찜을 누르는 사용자의 Id

    public LikeRequestDTO(String userId, Long itemId) {
        this.userId = userId;
        this.itemId = itemId;
    }
}
