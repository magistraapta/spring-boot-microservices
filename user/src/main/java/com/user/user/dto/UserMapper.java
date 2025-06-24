package com.user.user.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

import com.user.user.entity.User;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "USER")
    User toEntity(UserRequest userRequest);

    @Mapping(target = "role", source = "role")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "password", source = "password")
    UserResponse toResponse(User user);
}
