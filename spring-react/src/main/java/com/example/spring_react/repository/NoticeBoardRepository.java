package com.example.spring_react.repository;

import com.example.spring_react.entity.NoticeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {

    @Query(value = "SELECT n FROM NoticeBoard n WHERE n.title LIKE %:searchKey% OR n.uploader.nickname LIKE %:searchKey%")
    Page<NoticeBoard> findAllByQuery(Pageable pageable, @Param("searchKey")String searchKey);
}
