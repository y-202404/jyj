package com.example.spring_react.dto;

import com.example.spring_react.entity.Member;
import com.example.spring_react.entity.NoticeBoard;
import lombok.Data;

@Data
public class NoticeDTO {
    private Long id;
    private String title;
    private String content;
    private Member uploader;
    private String createDate;
    private Integer readCount;

    public NoticeBoard createNoticeBoard(NoticeDTO noticeDTO) {
        return new NoticeBoard(id, title, content, uploader, createDate, readCount);
    }
}
