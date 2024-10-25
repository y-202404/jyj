package com.example.nestco.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"item_id", "user_id"}) // 중복 방지를 위한 유니크 설정
})
public class ItemLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne
    @JoinColumn(name="item_id")
    private NestcoItems item;

    @ManyToOne
    @JoinColumn(name="user_id")
    private  Member user;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp likeDate;
}
