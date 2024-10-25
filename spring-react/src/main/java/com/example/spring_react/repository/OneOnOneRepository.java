package com.example.spring_react.repository;

import com.example.spring_react.entity.OneOnOne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OneOnOneRepository extends JpaRepository<OneOnOne, Long> {
}
