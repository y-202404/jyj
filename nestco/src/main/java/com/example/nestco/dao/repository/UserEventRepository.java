package com.example.nestco.dao.repository;

import com.example.nestco.models.entity.Member;
import com.example.nestco.models.entity.UserEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEventRepository extends JpaRepository<UserEvent, Long> {
    UserEvent findByMemberId(Member member);
}
