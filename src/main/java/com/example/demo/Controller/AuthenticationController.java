package com.example.demo.Controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.IAuthenticationService;
import com.example.demo.model.request.*;
import com.example.demo.model.response.AuthenticationResponse;
import com.example.demo.model.response.IntrospectTokenResponse;
import com.nimbusds.jose.JOSEException;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private IAuthenticationService authenticationService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        var result = authenticationService.authenticate(authenticationRequest);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest TokenRequest) throws ParseException, JOSEException {
        authenticationService.logout(TokenRequest);
        ;
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectTokenResponse> authenticate(@RequestBody IntrospectTokenRequest introspectTokenRequest)
            throws ParseException, JOSEException {
        var result = authenticationService.introspectTokenResponse(introspectTokenRequest);
        return ApiResponse.<IntrospectTokenResponse>builder().result(result).build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshToken refreshToken)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(refreshToken);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }
}
