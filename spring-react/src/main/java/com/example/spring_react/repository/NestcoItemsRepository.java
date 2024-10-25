package com.example.spring_react.repository;

import com.example.spring_react.entity.NestcoItems;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NestcoItemsRepository extends JpaRepository<NestcoItems, Long> {

    @Query(value = "SELECT n FROM NestcoItems n WHERE n.status = :logic%")
    Page<NestcoItems> findAllByQuery(Pageable pageable, @Param("logic")Boolean logic);

}
