package com.example.spring_react.repository;

import com.example.spring_react.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, String> {
    public Member findByNickname(String nickname);

    public Member findByUserId(String userId);

    public Member findByUsername(String username);

    public Member findByPhoneNumber(String phoneNumber);

    public Member findByProvider(String provider);

    public Member findByEmail(String Email);

    public List<Member> findAllByUserPassword(String userPassword);

    public List<Member> findAllByUsername(String username);

    public List<Member> findAllByProvider(String provider);

    @Query(value = "SELECT m FROM Member m WHERE m.userId LIKE %:searchKey% OR m.username LIKE %:searchKey%")
    public Page<Member> findAllByQuery(Pageable pageable, @Param("searchKey")String searchKey);

    @Override
    ArrayList<Member> findAll();
}
