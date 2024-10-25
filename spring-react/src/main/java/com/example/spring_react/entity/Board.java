package com.example.spring_react.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Board {
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

    @Column
    private Integer readCount;

    @PrePersist
    public void createDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        this.createDate = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void update(Board board) {
        if(board.getTitle() != null) {
            this.title = board.title;
        }

        if(board.getContent() != null) {
            this.content = board.content;
        }

        if(board.getReadCount() != null) {
            this.readCount = board.readCount;
        }
    }
}
