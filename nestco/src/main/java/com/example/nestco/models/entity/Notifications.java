//package com.example.nestco.models.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.CurrentTimestamp;
//
//import java.sql.Timestamp;
//
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//public class Notifications {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private Member user;
//
//    @Column
//    private boolean is_read;
//
//    @CreationTimestamp
//    @Column(updatable = false)
//    private Timestamp create_at;   // 등록일
//
//
//}
