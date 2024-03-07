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
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    @CreationTimestamp
    private LocalDateTime time;
    @BatchSize(size = 10)
    @ManyToOne(fetch = FetchType.LAZY)
    private User sender;
    @BatchSize(size = 10)
    @ManyToOne(fetch = FetchType.LAZY)
    private User recipient;
    @BatchSize(size = 10)
    @ManyToOne(fetch = FetchType.LAZY)
    private Chat chat;
    private Boolean isRead = false;
}