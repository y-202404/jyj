package com.example.nestco.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponseDTO {
    private Long likeId;
    private Long itemId;          // 아이템 ID
    private String itemName;      // 아이템 이름
    private String userId;  // 올린 사람 (유저 이름)
    private String uploaderName;
    private Timestamp likeDate;   // 찜한 날짜
    private String transactionStatus;  // 거래 상태
    private String thumbnailPath;
    private boolean isLike;       // 찜 여부
    private String message;       // 응답 메시지

}
