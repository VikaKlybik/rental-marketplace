package com.bsuir.entity;

import com.bsuir.enums.PropertyStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private PropertyStatus propertyStatus;
    private String title;
    private String description;
    private BigDecimal price;
    @BatchSize(size = 10)
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;
    @BatchSize(size = 10)
    @ManyToOne(fetch = FetchType.EAGER)
    private PropertyCategory propertyCategory;
    @BatchSize(size = 10)
    @OneToOne(fetch = FetchType.EAGER)
    private GeolocationData geolocationData;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "property")
    private List<AttributeValue> attributeValueList;
    @OneToMany(mappedBy = "property")
    private List<Image> imageList;
}