package com.bsuir.dto;

import com.bsuir.enums.Datatype;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttributeResponse implements Serializable {
    private Long id;
    private String name;
    private Datatype dataType;
    private AttributeGroupResponse attributeGroup;
}