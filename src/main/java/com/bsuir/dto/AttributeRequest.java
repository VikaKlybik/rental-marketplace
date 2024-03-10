package com.bsuir.dto;

import com.bsuir.enums.Datatype;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttributeRequest {
    private String name;
    private Datatype dataType;
    private Long attributeGroupId;
}