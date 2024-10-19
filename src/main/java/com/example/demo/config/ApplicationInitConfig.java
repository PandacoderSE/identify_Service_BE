package com.example.demo.config;

import com.example.demo.Entity.UserEntity;
import com.example.demo.Repository.UserRepository;
import com.example.demo.enums.Role;
import com.example.demo.model.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
// viet log thì dùng ano này
@Slf4j
public class ApplicationInitConfig {
    // kiểm tra tài khoản admin chưa tồn tại thì phải tạo 1 cái duy nhất
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            HashSet<String> roles = new HashSet<>() ;
            roles.add(Role.ADMIN.name()) ;
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10) ;
            if(userRepository.findByUsername("admin") == null){
                UserEntity user = UserEntity.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("123456"))
                        .roles(roles).build() ;
                userRepository.save(user) ;
                log.warn("admin user has been created with default password , admin please change it");
            }
        } ;
    }
}
