package com.example.demo.Service.Impl;

import com.example.demo.Entity.UserEntity;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.IAuthenticationService;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.model.request.AuthenticationRequest;
import com.example.demo.model.request.IntrospectTokenRequest;
import com.example.demo.model.response.AuthenticationResponse;
import com.example.demo.model.response.IntrospectTokenResponse;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {
    @Autowired
    private UserRepository userRepository ;
    @NonFinal
//    protected static final String SIGNER_KEY = "WHoXAMeaioBEovwVedUCB6lTNgEHL1zUGN/j9cpPmkqkceAvSFHmxTfm4rAvH069" ;
    @Value("${jwt.signerKey}")
    private String SIGNER_KEY ;
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10) ;
        UserEntity userFind = userRepository.findByUsername(request.getUsername());

        boolean authenticated =  passwordEncoder.matches(request.getPassword(), userFind.getPassword());
        if(!authenticated){
            throw new AppException(ErrorCode.UNAUTHENTICATED) ;
        }
        //tạo token nếu đăng nhập thành công
        var token = generateToken(userFind) ;
        // buider ở lombok
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    @Override
    public IntrospectTokenResponse introspectTokenResponse(IntrospectTokenRequest introspectTokenRequest) throws JOSEException, ParseException {
        var token = introspectTokenRequest.getToken() ;
        // kiêm tra  token đúng hay ko
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token) ;
        // kểm tra thời hạn token
        Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime() ;

        var verified =  signedJWT.verify(verifier) ;
        return  IntrospectTokenResponse.builder()
                .valid(verified && expityTime.after(new Date()))
                .build();
    }

    public String generateToken(UserEntity user){
        // tạo header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512) ; // chọn thuật toans
        //tạo payload
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("vanmanh.com") // domain
                .issueTime(new Date()) // thời gian
                .expirationTime(new Date( // thời gian hết hạn
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope",buildScope(user)) // để có thể autherization cần role
                .build() ;
        Payload payload = new Payload(jwtClaimsSet.toJSONObject()) ;
        // build thông tin token
        JWSObject jwsObject = new JWSObject(header,payload) ;
        // ký token
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes())); // ký bằng các thuật toán đc chọn
            return jwsObject.serialize() ;
        } catch (JOSEException e) {
            throw new RuntimeException("Not define J TOKEN");
        }

    }

    //tạo scope từ user
    private String buildScope(UserEntity user){
        StringJoiner stringJoiner = new StringJoiner(" ") ;
        if(!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles().forEach( s -> stringJoiner.add(s));
        }
        return stringJoiner.toString() ;
    }
}
