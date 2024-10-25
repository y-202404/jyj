package com.example.spring_react.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NoticeBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;


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

    public void update(NoticeBoard noticeBoard) {
        if(noticeBoard.getReadCount() != null) {
            this.readCount = noticeBoard.getReadCount();
        }
    }

}
