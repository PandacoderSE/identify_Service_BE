package com.example.demo.Service;

import com.example.demo.Entity.UserEntity;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.response.UserResponse;

import java.util.List;

public interface IUserService {
    public UserResponse createUser(UserDTO dto) ;
    public UserDTO findU(Long Id) ;

    UserEntity updateUser(Long id , UserDTO dto) ;
    List<UserResponse> getAll() ;
}
