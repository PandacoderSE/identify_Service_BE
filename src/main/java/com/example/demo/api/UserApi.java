package com.example.demo.api;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Service.IUserService;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.request.ApiResponse;
import com.example.demo.model.request.UpdateUserRequest;
import com.example.demo.model.response.UserResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserApi {
    @Autowired
    private IUserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserDTO dto) {
        log.info("controller : create User");
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userService.createUser(dto))
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> listUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        // ghi log
        log.info("Username :{}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        List<UserResponse> list = userService.getAll();
        return ApiResponse.<List<UserResponse>>builder().code(1000).result(list).build();
    }

    @GetMapping("/{id}")
    ApiResponse<UserResponse> getUser(@PathVariable Long id) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(id))
                .build();
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest user) {
        return userService.updateUser(id, user);
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }
}
