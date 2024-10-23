package com.example.demo.model.dto;

import com.example.demo.validator.DobConstraint;
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
    @Size(min = 6, message = "INVALID_PASSWORD")
    private String password ;


    private String firstname ;


    private String lastname ;
    @DobConstraint(min = 16, message ="INVALID_DOB")
    private LocalDate dob;

}
