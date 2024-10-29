package com.example.demo.Service;

import java.util.List;

import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.request.UpdateUserRequest;
import com.example.demo.model.response.UserResponse;

public interface IUserService {
    public UserResponse createUser(UserDTO dto);

    public UserResponse getUser(Long id);

    UserResponse updateUser(Long id, UpdateUserRequest request);

    List<UserResponse> getAll();

    UserResponse getMyInfo();
}
