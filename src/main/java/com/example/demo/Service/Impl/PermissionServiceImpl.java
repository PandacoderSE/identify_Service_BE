package com.example.demo.Service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.PermissionEntity;
import com.example.demo.Repository.PermissionRepository;
import com.example.demo.Service.IPermissionService;
import com.example.demo.model.request.PermissionRequest;
import com.example.demo.model.response.PermissionResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PermissionResponse createPer(PermissionRequest request) {
        PermissionEntity permissionEntity = modelMapper.map(request, PermissionEntity.class);
        return modelMapper.map(permissionRepository.save(permissionEntity), PermissionResponse.class);
    }

    @Override
    public List<PermissionResponse> getAlls() {
        List<PermissionEntity> list = permissionRepository.findAll();
        List<PermissionResponse> listRes = new ArrayList<>();
        for (PermissionEntity item : list) {
            PermissionResponse res = modelMapper.map(item, PermissionResponse.class);
            listRes.add(res);
        }
        return listRes;
    }

    @Override
    public void deletePer(String permisstionName) {

        permissionRepository.deleteById(permisstionName);
    }
}
