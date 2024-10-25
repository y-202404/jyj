package com.example.spring_react.entity;

import com.example.spring_react.dto.NestcoForm;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Setter
public class NestcoItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;        // 등록글 ID

    @Column(nullable = false)
    private String title;   // 제목

    @Column (nullable = false)
    private String content; // 내용

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;    // 아이템과 카테고리 다대일

    @ColumnDefault("0")
    private Boolean status; // 거래완료 여부

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @ColumnDefault("0")
    private int boardHits;  // 조회수

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createDate;   // 등록일

    @UpdateTimestamp
    @Column(insertable = false)
    private Timestamp updateDate;   // 수정일

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member uploader;  // 업로더정보

    @Transient
    private String imagePath; // 임시로 이미지 경로를 저장할 필드 (DB에 저장되지 않음)

    // 이미지 경로 설정 메서드
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @OneToMany(mappedBy = "items", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ItemThumbnail> thumbnails = new ArrayList<>();

    // 썸네일 추가 메서드
    public void addThumbnail(ItemThumbnail thumbnail) {
        if (thumbnail == null || thumbnail.getImagePath() == null || thumbnail.getImagePath().isEmpty()) {
            log.error("유효하지 않은 썸네일 경로입니다.");
            throw new IllegalArgumentException("유효하지 않은 썸네일 경로입니다.");
        }
        this.thumbnails.add(thumbnail);
        thumbnail.setItems(this);  // 연관 관계 설정
        log.info("썸네일이 성공적으로 추가되었습니다: {}", thumbnail.getImagePath());
    }

    // 썸네일 제거 메서드
    public void removeThumbnail(ItemThumbnail thumbnail) {
        this.thumbnails.remove(thumbnail);
        thumbnail.setItems(null);
    }

    // 조회수 증가 메서드
    public void incrementHits() {
        this.boardHits++;
    }

    // 최근 등록 물품인지 확인하는 메서드
    public boolean isNew() {
        return createDate.toLocalDateTime().isAfter(LocalDateTime.now().minusDays(7));
    }

    // 업로드 엔티티로 변환하는 메서드
    public static NestcoItems toUploadEntity(NestcoForm form, Category category, Member uploader, List<String> imagePaths) {
        log.info("이미지 경로 리스트: {}", imagePaths);
        if (imagePaths == null || imagePaths.isEmpty()) {
            log.error("이미지 경로 리스트가 비어 있습니다.");
            throw new IllegalArgumentException("이미지 경로 리스트가 비어 있습니다.");
        }

        NestcoItems newItem = NestcoItems.builder()
                .title(form.getTitle())
                .content(form.getContent())
                .category(category)
                .status(false)
                .boardHits(0)
                .uploader(uploader)
                .build();

        for (String imagePath : imagePaths) {
            ItemThumbnail thumbnail = new ItemThumbnail(newItem, imagePath, null);
            log.info("썸네일 추가: {}", imagePath);
            newItem.addThumbnail(thumbnail);
        }
        return newItem;
    }

    // 등록 시간 계산 메서드
    public String getTimeSincePosted() {
        // Timestamp를 LocalDateTime으로 변환
        LocalDateTime postedDateTime = createDate.toLocalDateTime();
        // 현재 시간과의 차이를 계산
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(postedDateTime, now);

        long hours = duration.toHours();
        long days = duration.toDays();

        if (days > 0) {
            return days + "일 전";
        } else if (hours > 0) {
            return hours + "시간 전";
        } else {
            return "방금 전";
        }
    }
}