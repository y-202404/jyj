package com.example.nestco.dao.repository;

import com.example.nestco.models.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query(value = "SELECT b FROM Board AS b WHERE b.title LIKE %:searchKey% OR uploader.nickname LIKE %:searchKey%")
    Page<Board> findAllByQuery(Pageable newPageable, @Param("searchKey") String searchKey);
}
