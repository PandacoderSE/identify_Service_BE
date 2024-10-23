package com.example.demo.Service;

import com.example.demo.model.request.AuthenticationRequest;
import com.example.demo.model.request.IntrospectTokenRequest;
import com.example.demo.model.request.LogoutRequest;
import com.example.demo.model.response.AuthenticationResponse;
import com.example.demo.model.response.IntrospectTokenResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface IAuthenticationService {
    public AuthenticationResponse authenticate(AuthenticationRequest request) ;
    void logout(LogoutRequest request) throws JOSEException, ParseException ;
    IntrospectTokenResponse introspectTokenResponse(IntrospectTokenRequest introspectTokenRequest) throws JOSEException, ParseException;
}
