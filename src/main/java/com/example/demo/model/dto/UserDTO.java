package com.example.demo.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private long id ;

    private String username ;
    @Size(min = 8, message = "Password must be at least 8 char ")
    private String password ;


    private String firstname ;


    private String lastname ;

    private LocalDate dob;

}
