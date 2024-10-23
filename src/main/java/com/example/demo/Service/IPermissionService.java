package com.example.demo.Service;

import com.example.demo.model.request.PermissionRequest;
import com.example.demo.model.response.PermissionResponse;

import java.util.List;

public interface IPermissionService {
    PermissionResponse createPer(PermissionRequest request) ;
    List<PermissionResponse> getAlls() ;
    void deletePer(String permisstionName) ;
}
