package com.example.demo.Service;

import java.util.List;

import com.example.demo.model.request.PermissionRequest;
import com.example.demo.model.response.PermissionResponse;

public interface IPermissionService {
    PermissionResponse createPer(PermissionRequest request);

    List<PermissionResponse> getAlls();

    void deletePer(String permisstionName);
}
