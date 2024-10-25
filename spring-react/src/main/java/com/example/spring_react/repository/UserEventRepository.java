package com.example.spring_react.repository;

import com.example.spring_react.entity.Member;
import com.example.spring_react.entity.UserEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEventRepository extends JpaRepository<UserEvent, Long> {

    UserEvent findByMemberId(Member member);
}
