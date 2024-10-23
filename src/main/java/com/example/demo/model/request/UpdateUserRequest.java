package com.example.demo.model.request;

import com.example.demo.model.response.RoleResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    private long id ;

    private String username ;
    private String firstname ;
    private String password ;

    private String lastname ;

    private LocalDate dob;

    private Set<String> roles;
}