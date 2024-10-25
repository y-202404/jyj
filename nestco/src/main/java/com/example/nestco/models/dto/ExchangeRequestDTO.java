package com.example.nestco.models.dto;

import com.example.nestco.models.entity.Member;
import com.example.nestco.models.entity.NestcoItems;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExchangeRequestDTO {
    private String requesterId;        // 요청자의 ID (Member 대신)
    private String requesterName;    // 요청자의 닉네임
    private String receiverId;         // 수신자의 ID
    private Long uploaderItemId;     // 업로더 아이템 ID
    private String uploaderItemTitle;// 업로더 아이템 제목
    private Long exchangeItemId;     // 교환할 아이템 ID
    private String exchangeItemTitle;// 교환할 아이템 제목
    private String message;          // 교환 요청 메시지
    private Boolean accepted;            // 교환 수락 여부

    // DTO 생성자
    public ExchangeRequestDTO(String requesterId, String requesterName, String receiverId, Long uploaderItemId, String uploaderItemTitle, Long exchangeItemId, String exchangeItemTitle, String message, boolean accepted) {
        this.requesterId = requesterId;
        this.requesterName = requesterName;
        this.receiverId = receiverId;
        this.uploaderItemId = uploaderItemId;
        this.uploaderItemTitle = uploaderItemTitle;
        this.exchangeItemId = exchangeItemId;
        this.exchangeItemTitle = exchangeItemTitle;
        this.message = message;
        this.accepted = accepted;
    }
}
