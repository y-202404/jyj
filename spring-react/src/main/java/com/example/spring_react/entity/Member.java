package com.example.spring_react.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter


public class Member {
    @Id
    @Column(name="user_id")
    private String userId;

    @Column
    private String userPassword;

    @Column
    private String username;

    @Column
    private String nickname;

    @Column
    private String email;

    @Column
    private String phoneNumber;

    @Column
    private Integer postalAddress;

    @Column
    private String address;

    @Column
    private String detailedAddress;

    @Column
    private String provider;

    @Column
    private String role;

    @Column
    private String createDate;

    @Column
    private String block;

    @Column
    private String profileImagePath;

    @Column
    private Integer declaration;

    @PrePersist
    public void createDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        this.createDate = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public void update(Member member) {
        if(!Objects.isNull(member)) {
            if(member.userPassword != null) {
                this.userPassword = member.userPassword;
            }
            if(member.nickname != null) {
                this.nickname = member.nickname;
            }
            if(member.phoneNumber != null) {
                this.phoneNumber = member.phoneNumber;
            }
            if(member.postalAddress != null) {
                this.postalAddress = member.postalAddress;
            }
            if(member.address != null) {
                this.address = member.address;
            }
            if(member.detailedAddress != null) {
                this.detailedAddress = member.detailedAddress;
            }
            if(member.block != null) {
                this.block = member.block;
            }
            if(member.declaration != null) {
                this.declaration = member.declaration;
            }
        }
    }

}