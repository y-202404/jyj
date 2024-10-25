package com.example.spring_react.repository;

import com.example.spring_react.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query(value = "SELECT b FROM Board b WHERE b.title LIKE %:searchKey% OR b.uploader.nickname LIKE %:searchKey%")
    Page<Board> findAllByQuery(Pageable pageable, @Param("searchKey")String searchKey);
}
