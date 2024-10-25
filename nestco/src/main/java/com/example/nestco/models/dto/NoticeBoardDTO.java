package com.example.nestco.models.dto;

import com.example.nestco.models.entity.Member;
import com.example.nestco.models.entity.NoticeBoard;
import lombok.Data;

@Data
public class NoticeBoardDTO {

    private Long id;
    private String title;
    private String content;
    private Member uploader;    //member?
    private String createDate;  //@perPersist에서 자동으로 설정해서 값을 설정할 필요없긴 함
    private Integer readCount;

    public NoticeBoard createNoticeBoard() {
        return new NoticeBoard(id, title, content, uploader, createDate, readCount);
    }
}
