package com.example.demo.Service;

import java.util.List;

import com.example.demo.model.request.RoleRequest;
import com.example.demo.model.response.RoleResponse;

public interface IRoleService {
    RoleResponse createRole(RoleRequest request);

    List<RoleResponse> getAlls();

    void deleteRole(String name);
}
