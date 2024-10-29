package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.Entity.UserEntity;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.request.UpdateUserRequest;
import com.example.demo.model.response.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toUser(UserDTO dto);

    UserResponse toRespon(UserEntity enty);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "roles", ignore = true) // Bỏ qua trường roles
    void updateUser(@MappingTarget UserEntity ue, UpdateUserRequest up);
}
