package com.example.spring_react.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ItemThumbnail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="thumbnail_id")
    private Long thumbnailId;    //PK


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private NestcoItems items;      //아이템과 다대일 관계

    @Column(name="image_path", nullable = false)
    private String imagePath;   //사진 파일 경로

    private Long fileSize;

    @Column(name="created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt; //생성일

    @Builder
    public ItemThumbnail(NestcoItems items,String imagePath, Long fileSize) {
        this.items = items;
        this.imagePath = imagePath;
        this.fileSize = fileSize;
        this.createdAt = LocalDateTime.now();

        log.info("ItemThumbnail 생성 - 경로: {}, 크기: {}", imagePath, fileSize);
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
