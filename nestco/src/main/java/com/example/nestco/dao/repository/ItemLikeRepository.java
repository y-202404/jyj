package com.example.nestco.dao.repository;

import com.example.nestco.models.entity.Member;
import com.example.nestco.models.entity.NestcoItems;
import com.example.nestco.models.entity.ItemLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemLikeRepository extends JpaRepository<ItemLike, Long> {

    Optional<ItemLike> findByItemIdAndUser_UserId(Long itemId, String userId);
    List<ItemLike> findAllByUser_UserId(String userId);
    void deleteByItemIdAndUser_UserId(Long itemId, String userId);
    }
