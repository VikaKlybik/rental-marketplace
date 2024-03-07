package com.bsuir.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttributeGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String groupName;
    @BatchSize(size = 10)
    @ManyToOne(fetch = FetchType.LAZY)
    private PropertyCategory propertyCategory;
    @OneToMany(mappedBy = "attributeGroup")
    private List<Attribute> attributes;
}