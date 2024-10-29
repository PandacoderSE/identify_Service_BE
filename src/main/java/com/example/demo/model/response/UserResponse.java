package com.example.demo.model.response;

import java.time.LocalDate;
import java.util.Set;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private long id;

    private String username;

    private String firstname;

    private String lastname;

    private LocalDate dob;

    private Set<RoleResponse> roles;
}
