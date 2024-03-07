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
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String title;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @BatchSize(size = 10)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private String imageUrl;
    private String description;
}