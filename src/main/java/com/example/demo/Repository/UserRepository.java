package com.example.demo.Repository;

import com.example.demo.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity , Long> {
    boolean existsByUsername(String name) ;
    UserEntity findByUsername(String name) ;
}
