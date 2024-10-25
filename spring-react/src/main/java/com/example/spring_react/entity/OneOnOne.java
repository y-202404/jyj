package com.example.spring_react.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OneOnOne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column
    private String type;

    @ManyToOne
    @JoinColumn(name="user_id")
    private Member memberId;

    @Column
    private String phoneNumber;

    @Column
    private String content;

}
