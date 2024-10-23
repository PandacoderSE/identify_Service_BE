package com.example.demo.model.request;

import com.example.demo.model.response.PermissionResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PermissionRequest {
    private String name ;

    private String description ;
}
