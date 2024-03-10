package com.bsuir.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static com.bsuir.constant.RentalPropertiesConstants.Validation.ErrorMessage.FIELD_EMPTY;
import static com.bsuir.constant.RentalPropertiesConstants.Validation.ErrorMessage.NOT_VALID_FORMAT;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyRequest {
    private Long id;
    @NotEmpty(message = FIELD_EMPTY)
    private String title;
    @NotEmpty(message = FIELD_EMPTY)
    private String description;
    @NotNull(message = FIELD_EMPTY)
    @Min(value = 0, message = NOT_VALID_FORMAT)
    private BigDecimal price;
    @NotNull(message = FIELD_EMPTY)
    @Min(value = 0, message = NOT_VALID_FORMAT)
    private Long propertyCategoryId;
}