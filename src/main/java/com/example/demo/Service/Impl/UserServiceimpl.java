package com.example.demo.Service.Impl;

import com.example.demo.Entity.UserEntity;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.IUserService;
import com.example.demo.enums.Role;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceimpl implements IUserService {

    @Autowired
    private UserRepository userRepository ;
    @Autowired
    private ModelMapper modelMapper ;
    @Autowired
    private UserMapper userMapper ;
    @Override
    public UserResponse createUser(UserDTO dto) {
        if(userRepository.existsByUsername(dto.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
            UserEntity newu = userMapper.toUser(dto) ;
            PasswordEncoder  passwordEncoder = new BCryptPasswordEncoder(10) ;
            newu.setPassword(passwordEncoder.encode(dto.getPassword())) ;
            HashSet<String> roles = new HashSet<>() ;
            roles.add(Role.USER.name()) ;
            newu.setRoles(roles);
        return userMapper.toRespon(userRepository.save(newu)) ;
    }
    @PostAuthorize("returnObject.username == authentication.name") // đúng user thì trả về thông tin user đó
    @Override
    public UserResponse getUser(Long id){
        log.info("In method get user by Id");
        return userMapper.toRespon(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED)));
    }

    @Override
    public UserEntity updateUser(Long id, UserDTO dto) {
        UserEntity us = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));;
        userMapper.updateUser(us, dto);
        return userRepository.save(us) ;
    }
    // cách dùng method phân quyền
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<UserResponse> getAll() {
        List<UserResponse> list = new ArrayList<>() ;
        List<UserEntity> liste = userRepository.findAll() ;
        for(UserEntity t : liste){
            UserResponse dto = userMapper.toRespon(t) ;
            list.add(dto) ;
        }
        return list;
    }

    @Override
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext() ;
        String name = context.getAuthentication().getName();
        UserEntity user = Optional.ofNullable(userRepository.findByUsername(name))
                .orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));
        return userMapper.toRespon(user) ;
    }
}
