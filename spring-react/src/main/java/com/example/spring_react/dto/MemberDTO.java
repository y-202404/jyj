package com.example.spring_react.dto;


import com.example.spring_react.entity.Member;
import lombok.Data;


import java.sql.Timestamp;

@Data
public class MemberDTO {
    private String userId;
    private String userPassword;
    private String username;
    private String nickname;
    private String email;
    private String phoneNumber;
    private Integer postalAddress;
    private String address;
    private String detailedAddress;
    private String provider;
    private String role;
    private String createDate;
    private String block;
    private String profileImagePath;
    private Integer declaration;

    public Member createMemberEntity(MemberDTO memberDTO) {
        return new Member(userId, userPassword, username, nickname, email, phoneNumber, postalAddress, address, detailedAddress, provider, role, createDate, block, profileImagePath,declaration);
    }
}