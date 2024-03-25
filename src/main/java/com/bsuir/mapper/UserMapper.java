package com.bsuir.mapper;

import com.bsuir.dto.UserDTO;
import com.bsuir.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(source = "userDetails.firstName", target = "firstName")
    @Mapping(source = "userDetails.lastName", target = "lastName")
    @Mapping(source = "userDetails.email", target = "email")
    @Mapping(source = "userDetails.phone", target = "phone")
    @Mapping(source = "userDetails.iconUrl", target = "iconUrl")
    UserDTO toDto(User user);
    List<UserDTO> toListOfDto(List<User> userList);
    User toEntity(UserDTO userDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserDTO userDTO, @MappingTarget User user);
}