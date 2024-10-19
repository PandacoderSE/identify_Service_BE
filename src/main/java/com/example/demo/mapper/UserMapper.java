package com.example.demo.mapper;

import com.example.demo.Entity.UserEntity;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toUser(UserDTO dto) ;
    UserResponse toRespon(UserEntity enty) ;
    @Mapping(target = "id", ignore = true)
    void updateUser(@MappingTarget UserEntity ue , UserDTO dto) ;
}
