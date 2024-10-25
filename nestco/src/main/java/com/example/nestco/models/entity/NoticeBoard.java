package com.example.nestco.models.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NoticeBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;


    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    // 작성자 정보를 Member 엔티티와 연관관계로 설정
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member uploader;

    @Column
    private String createDate;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer readCount = 0;

    @PrePersist
    public void createDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        this.createDate = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    // 조회수 증가 메서드
    public void incrementReadCount() {
        this.readCount += 1;
    }

}
