package com.example.nestco.dao.repository;

import com.example.nestco.models.entity.OneOnOne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OneOnOneRepository extends JpaRepository<OneOnOne, Long> {  //관리대상 과 대표값
}
