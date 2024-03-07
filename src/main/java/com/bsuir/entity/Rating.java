package com.bsuir.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private Float rating;
    @BatchSize(size = 10)
    @ManyToOne(fetch = FetchType.LAZY)
    private User forUser;
    @BatchSize(size = 10)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @CreationTimestamp
    private LocalDateTime createdAt;
}