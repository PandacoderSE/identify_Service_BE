package com.example.demo.Controller;

import com.example.demo.Service.IPermissionService;
import com.example.demo.model.request.ApiResponse;
import com.example.demo.model.request.PermissionRequest;
import com.example.demo.model.response.PermissionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@Slf4j
public class PermissionController {
    @Autowired
    private IPermissionService permissionService ;

    @PostMapping
    ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest permissionRequest){
        PermissionResponse per = permissionService.createPer(permissionRequest) ;
        return ApiResponse.<PermissionResponse>builder()
                .result(per)
                .build() ;
    }
    @GetMapping
    ApiResponse<List<PermissionResponse>> getALL(){
        return  ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAlls())
                .build() ;
    }
    @DeleteMapping("{id}")
    ApiResponse<Void> delete(@PathVariable String id){
        permissionService.deletePer(id) ;
        return ApiResponse.<Void>builder()
                .build() ;
    }

}
