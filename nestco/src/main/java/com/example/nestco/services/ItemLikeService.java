package com.example.nestco.services;

import com.example.nestco.dao.repository.ItemLikeRepository;

import com.example.nestco.dao.repository.MemberRepository;
import com.example.nestco.dao.repository.NestcoItemsRepository;
import com.example.nestco.models.dto.LikeRequestDTO;
import com.example.nestco.models.dto.LikeResponseDTO;
import com.example.nestco.models.entity.ItemLike;
import com.example.nestco.models.entity.Member;
import com.example.nestco.models.entity.NestcoItems;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemLikeService {
    private final ItemLikeRepository itemLikeRepository;
    private final NestcoItemsRepository nestcoItemsRepository;
    private final MemberRepository memberRepository;
    private final ItemThumbnailService itemThumbnailService;

    // 찜추가
    public LikeResponseDTO saveItemLike(LikeRequestDTO likeRequestDTO) {
        Long itemId = likeRequestDTO.getItemId();
        String userId = likeRequestDTO.getUserId();

        // 아이템과 사용자 조회
        NestcoItems item = nestcoItemsRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("아이템을 찾을 수 없습니다."));
        Member user = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 중복된 찜인지 확인
        Optional<ItemLike> existingLike = itemLikeRepository.findByItemIdAndUser_UserId(itemId, userId);
        if (existingLike.isPresent()) {
            throw new IllegalStateException("이미 찜한 아이템입니다.");
        }

        // 찜 저장 (기본 생성자를 사용하여 객체 생성 후 필드 설정)
        ItemLike itemLike = new ItemLike();
        itemLike.setItem(item);
        itemLike.setUser(user);
        itemLike = itemLikeRepository.save(itemLike); // 여기서 저장

        // LikeResponseDTO 생성
        LikeResponseDTO likeResponseDTO = new LikeResponseDTO();
        likeResponseDTO.setLikeId(itemLike.getLikeId());
        likeResponseDTO.setItemId(itemId);
        likeResponseDTO.setUserId(userId);
        likeResponseDTO.setLike(true);
        likeResponseDTO.setMessage("찜이 성공적으로 추가되었습니다.");

        return likeResponseDTO;
    }

    // 사용자가 찜한 아이템 목록 조회
    public List<LikeResponseDTO> getUserLikes(String userId) {
        List<ItemLike> likes = itemLikeRepository.findAllByUser_UserId(userId);
        List<LikeResponseDTO> likeResponseDTOList = new ArrayList<>();

        for (ItemLike like : likes) {
            LikeResponseDTO responseDTO = new LikeResponseDTO();
            responseDTO.setLikeId(like.getLikeId());
            responseDTO.setItemId(like.getItem().getId());
            responseDTO.setItemName(like.getItem().getTitle());  // 아이템 이름 설정
            responseDTO.setUserId(like.getItem().getUploader().getUserId());  // 올린 사람 설정
            responseDTO.setUploaderName(like.getItem().getUploader().getNickname());  // 찜한 날짜 설정
            responseDTO.setTransactionStatus(like.getItem().getStatus()? "거래 완료" : "거래 미완료");  // 거래 상태 설정
            String thumbnailPath = itemThumbnailService.getFirstThumbnailsForItems(Collections.singletonList(like.getItem().getId())).get(like.getItem().getId());
            responseDTO.setThumbnailPath(thumbnailPath != null ? thumbnailPath : "/images/default-thumbnail.svg");

            likeResponseDTOList.add(responseDTO);
        }

        return likeResponseDTOList;
    }

    // 찜 여부 확인
    public boolean isItemLikedByUser(String userId, Long itemId) {
        return itemLikeRepository.findByItemIdAndUser_UserId(itemId, userId).isPresent();
    }

    // 아이템 찜 취소
    public void unlikeItem(Long itemId, String userId) {
        itemLikeRepository.deleteByItemIdAndUser_UserId(itemId, userId);
    }

    // 유저가 찜한 아이템 목록 조회
    public List<ItemLike> getUserLikedItems(String userId) {
        return itemLikeRepository.findAllByUser_UserId(userId);
    }
}
