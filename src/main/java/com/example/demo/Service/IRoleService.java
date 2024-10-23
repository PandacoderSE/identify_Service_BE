package com.example.demo.Service;

import com.example.demo.model.request.RoleRequest;
import com.example.demo.model.response.RoleResponse;

import java.util.List;

public interface IRoleService {
    RoleResponse createRole(RoleRequest request) ;
    List<RoleResponse> getAlls() ;
    void deleteRole(String name) ;
}
