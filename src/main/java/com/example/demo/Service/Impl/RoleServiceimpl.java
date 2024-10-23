package com.example.demo.Service.Impl;

import com.example.demo.Entity.PermissionEntity;
import com.example.demo.Entity.RoleEntity;
import com.example.demo.Repository.PermissionRepository;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.Service.IRoleService;
import com.example.demo.model.request.RoleRequest;
import com.example.demo.model.response.PermissionResponse;
import com.example.demo.model.response.RoleResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class RoleServiceimpl  implements IRoleService {
    @Autowired
    private RoleRepository roleRepository ;
    @Autowired
    private ModelMapper modelMapper ;
    @Autowired
    private PermissionRepository permissionRepository ;

    @Override
    public RoleResponse createRole(RoleRequest request) {
        RoleEntity role = modelMapper.map(request,RoleEntity.class) ;
        // mối quan hệ n-n , nên phải thêm vô kia theo quan hệ
        Set<PermissionEntity> list =new HashSet<>(permissionRepository.findAllById(request.getPermissions())) ;
        role.setPermissions(list);
        role = roleRepository.save(role) ;
        RoleResponse roleRespon = modelMapper.map(role,RoleResponse.class) ;
        return roleRespon;
    }

    @Override
    public List<RoleResponse> getAlls() {
        List<RoleEntity> roleEntylist = roleRepository.findAll() ;
        List<RoleResponse> roleResList = new ArrayList<>() ;
        for (RoleEntity item : roleEntylist){
            RoleResponse res = modelMapper.map(item , RoleResponse.class) ;
            roleResList.add(res) ;
        }
        return roleResList;
    }

    @Override
    public void deleteRole(String name) {
        roleRepository.deleteById(name);
    }
}
