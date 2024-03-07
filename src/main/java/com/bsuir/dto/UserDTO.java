package com.bsuir.dto;

import com.bsuir.entity.Role;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.bsuir.constant.RentalPropertiesConstants.Validation.ErrorMessage.FIELD_EMPTY;

@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    @NotEmpty(message = FIELD_EMPTY)
    private String username;
    private Boolean isActive;
    @NotEmpty(message = FIELD_EMPTY)
    private String firstName;
    @NotEmpty(message = FIELD_EMPTY)
    private String lastName;
    @NotEmpty(message = FIELD_EMPTY)
    private String email;
    @NotEmpty(message = FIELD_EMPTY)
    private String phone;
    private String iconUrl;
    private Integer totalPropertyCount;
    private Integer allowPropertyCount;
    private List<Role> roles;
}