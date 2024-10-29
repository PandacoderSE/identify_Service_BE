package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, String> {}
