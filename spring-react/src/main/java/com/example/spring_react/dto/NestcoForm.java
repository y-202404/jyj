package com.example.spring_react.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NestcoForm {
    private String title;  // 제목
    private String content;  // 내용
    private Long categoryId;  // 선택된 카테고리 ID
    private List<MultipartFile> itemThumbnails;  // 이미지 파일들(다중)
}