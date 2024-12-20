package com.example.demo.model.request;

import java.time.LocalDate;
import java.util.Set;

import com.example.demo.validator.DobConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    private long id;

    private String username;
    private String firstname;
    private String password;

    private String lastname;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    private LocalDate dob;

    private Set<String> roles;
}
