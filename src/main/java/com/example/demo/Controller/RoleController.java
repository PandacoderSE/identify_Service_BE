package com.example.demo.Controller;

import com.example.demo.Service.IPermissionService;
import com.example.demo.Service.IRoleService;
import com.example.demo.model.request.ApiResponse;
import com.example.demo.model.request.PermissionRequest;
import com.example.demo.model.request.RoleRequest;
import com.example.demo.model.response.PermissionResponse;
import com.example.demo.model.response.RoleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@Slf4j
public class RoleController {
    @Autowired
    private IPermissionService permissionService ;
    @Autowired
    private IRoleService roleService ;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request){
        RoleResponse role = roleService.createRole(request) ;
        return ApiResponse.<RoleResponse>builder()
                .result(role)
                .build() ;
    }
    @GetMapping
    ApiResponse<List<RoleResponse>> getALL(){
        return  ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAlls())
                .build() ;
    }
    @DeleteMapping("{id}")
    ApiResponse<Void> delete(@PathVariable String id){
        roleService.deleteRole(id); ;
        return ApiResponse.<Void>builder().build() ;
    }

}
