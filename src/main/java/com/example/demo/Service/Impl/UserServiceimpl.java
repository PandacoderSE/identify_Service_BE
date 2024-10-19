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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
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

    @Override
    public UserDTO findU(Long Id) {
       return modelMapper.map(userRepository.findById(Id).orElseThrow(() -> new RuntimeException("User not found ")),UserDTO.class) ;
    }

    @Override
    public UserEntity updateUser(Long id, UserDTO dto) {
        UserEntity us = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));;
        userMapper.updateUser(us, dto);
        return userRepository.save(us) ;
    }

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
}
