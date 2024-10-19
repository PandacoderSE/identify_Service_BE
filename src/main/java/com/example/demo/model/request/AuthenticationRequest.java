package com.example.demo.model.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// kểm tra hiệu lực của token
public class AuthenticationRequest {
    private String username ;
    @Size(min = 8, message = "Password must be at least 8 char ")
    private String password ;
}
