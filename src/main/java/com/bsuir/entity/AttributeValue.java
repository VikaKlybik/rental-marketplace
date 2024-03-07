package com.bsuir.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttributeValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @BatchSize(size = 10)
    @ManyToOne(fetch = FetchType.LAZY)
    private Attribute attribute;
    private String value;
    @ManyToOne(fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    private Property property;
}