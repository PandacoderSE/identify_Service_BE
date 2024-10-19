package com.example.demo.Controller;

import com.example.demo.Service.IAuthenticationService;
import com.example.demo.model.request.ApiResponse;
import com.example.demo.model.request.AuthenticationRequest;
import com.example.demo.model.request.IntrospectTokenRequest;
import com.example.demo.model.response.AuthenticationResponse;
import com.example.demo.model.response.IntrospectTokenResponse;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/auth")

public class AuthenticationController {
    @Autowired
    private IAuthenticationService authenticationService ;
    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest){
        var result =  authenticationService.authenticate(authenticationRequest) ;
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build() ;
    }
    @PostMapping("/introspect")
    ApiResponse<IntrospectTokenResponse> authenticate(@RequestBody IntrospectTokenRequest introspectTokenRequest) throws ParseException, JOSEException {
        var result =  authenticationService.introspectTokenResponse(introspectTokenRequest) ;
        return ApiResponse.<IntrospectTokenResponse>builder()
                .result(result)
                .build() ;
    }
}
