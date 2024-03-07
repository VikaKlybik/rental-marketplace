package com.bsuir.entity;

import com.bsuir.enums.Datatype;
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
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Datatype dataType;
    @BatchSize(size = 10)
    @ManyToOne(fetch = FetchType.LAZY)
    private AttributeGroup attributeGroup;
    @OneToMany(mappedBy = "attribute")
    private List<AttributeValue> attributeValueList;
}