package com.example.nestco.services;

import com.example.nestco.models.entity.ItemThumbnail;
import com.example.nestco.models.entity.NestcoItems;
import com.example.nestco.dao.repository.ItemThumbnailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = {Exception.class})
@RequiredArgsConstructor
public class ItemThumbnailService {

    private final ItemThumbnailRepository itemThumbnailRepository;


    // 파일 업로드 경로를 외부 설정에서 주입받음
    @Value("${file.upload-dir}")
    private String uploadDir;


    /**
     * 썸네일 등록
     */
    public void uploadThumbnail(NestcoItems items, List<MultipartFile> images) throws IOException {
        if (images == null || images.isEmpty()) {
            throw new IllegalArgumentException("이미지 파일이 비어 있습니다.");
        }

        log.info("이미지 파일 처리 중... 파일 수: {}", images.size());

        for (MultipartFile image : images) {
            log.info("이미지 파일 처리 중: {}", image.getOriginalFilename());
            String dbFilePath = saveImage(image);
            ItemThumbnail thumbnail = new ItemThumbnail(items, dbFilePath, image.getSize());
            itemThumbnailRepository.save(thumbnail);
            log.info("썸네일 저장 완료. 경로: {}, 파일 크기: {} bytes", dbFilePath, image.getSize());
        }
    }

    // 이미지 파일을 저장하는 메서드
    @Transactional
    public String saveImage(MultipartFile image) throws IOException {
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        String filePath = uploadDir + File.separator + fileName;
        String dbFilePath = "/uploads/thumbnails/" + fileName;

        log.info("파일 저장 경로: {}", filePath);

        Path path = Paths.get(filePath);
        if (!Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }

        try {
            Files.write(path, image.getBytes());
            log.info("파일이 성공적으로 저장되었습니다: {}", filePath);
        } catch (IOException e) {
            log.error("파일 저장 중 오류 발생: {}", e.getMessage());
            throw new IOException("파일 저장 중 오류가 발생했습니다.", e);
        }

        return dbFilePath;
    }


    // 아이템별로 첫 번째 썸네일 경로를 가져오는 메서드
    public Map<Long, String> getFirstThumbnailsForItems(List<Long> itemIds) {
        List<ItemThumbnail> thumbnails = itemThumbnailRepository.findByItems_IdIn(itemIds);
        Map<Long, String> firstThumbnailsMap = thumbnails.stream()
                .collect(Collectors.toMap(
                        thumbnail -> thumbnail.getItems().getId(),  // Key: 아이템 ID
                        ItemThumbnail::getImagePath,                // Value: 첫 번째 썸네일 경로
                        (existing, replacement) -> existing));      // 첫 번째 값만 사용

        return firstThumbnailsMap;
    }

    public List<String> getAllThumbnailsForItem(Long itemId) {
        List<ItemThumbnail> thumbnails = itemThumbnailRepository.findByItems_Id(itemId);
        return thumbnails.stream()
                .map(ItemThumbnail::getImagePath)  // 경로만 추출
                .collect(Collectors.toList());
    }
}
