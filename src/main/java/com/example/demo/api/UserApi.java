package com.example.demo.api;

import com.example.demo.Entity.UserEntity;
import com.example.demo.Service.IUserService;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.request.ApiResponse;
import com.example.demo.model.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserApi {
    @Autowired
    private IUserService userService ;

    @PostMapping
    ApiResponse<UserResponse> createUser (@RequestBody @Valid UserDTO dto){
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userService.createUser(dto))
                .build();
    }
    @GetMapping
    public ApiResponse<List<UserResponse>> listUsers(){
        List<UserResponse> list = userService.getAll() ;
        return ApiResponse.<List<UserResponse>>builder()
                .code(1000)
                .result(list)
                .build();
    }
    @GetMapping("/{id}")
    public UserDTO listUser( @PathVariable Long id){
            return userService.findU(id) ;
    }
    @PutMapping("/{id}")
    public UserEntity updateUser(@PathVariable Long id, @RequestBody UserDTO dto){
         return userService.updateUser(id, dto) ;
    }
}
