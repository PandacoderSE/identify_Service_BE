package com.example.demo.Service.Impl;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.demo.Entity.InvalidatedToken;
import com.example.demo.Entity.RoleEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.Repository.InvalidatedTokenRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.IAuthenticationService;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.model.request.AuthenticationRequest;
import com.example.demo.model.request.IntrospectTokenRequest;
import com.example.demo.model.request.LogoutRequest;
import com.example.demo.model.request.RefreshToken;
import com.example.demo.model.response.AuthenticationResponse;
import com.example.demo.model.response.IntrospectTokenResponse;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthenticationServiceImpl implements IAuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvalidatedTokenRepository tokenRepository;

    @NonFinal
    //    protected static final String SIGNER_KEY = "WHoXAMeaioBEovwVedUCB6lTNgEHL1zUGN/j9cpPmkqkceAvSFHmxTfm4rAvH069"
    // ;
    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;

    @Value("${jwt.valid-duration}")
    private Long VALID_DURATION;

    @Value("${jwt.refreshable-duration}")
    private Long REFRESH_DURATION;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info(SIGNER_KEY);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        UserEntity userFind = userRepository.findByUsername(request.getUsername());

        boolean authenticated = passwordEncoder.matches(request.getPassword(), userFind.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        // tạo token nếu đăng nhập thành công
        var token = generateToken(userFind);
        // buider ở lombok
        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    @Override
    public void logout(LogoutRequest request) throws JOSEException, ParseException {
        try {
            var signToken = verifiedToken(request.getToken(), true);
            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date extryTime = signToken.getJWTClaimsSet().getExpirationTime();
            InvalidatedToken tokenTime = new InvalidatedToken();
            tokenTime.setId(jit);
            tokenTime.setExpiryTime(extryTime);
            tokenRepository.save(tokenTime);
        } catch (AppException e) {
            log.info("token hết hạn");
        }
    }
    // hàm tar về token phục vụ hàm logout , verifi ra token
    private SignedJWT verifiedToken(String token, boolean isRefresh) throws JOSEException, ParseException {

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);
        // kểm tra thời hạn token , nếu true thì dùng cho refesh , còn ngược lại dùng cho xác thực và authentica
        Date expityTime = (isRefresh)
                ? new Date(signedJWT
                        .getJWTClaimsSet()
                        .getIssueTime()
                        .toInstant()
                        .plus(REFRESH_DURATION, ChronoUnit.SECONDS)
                        .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);
        if (!(verified && expityTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);

        String jit = signedJWT.getJWTClaimsSet().getJWTID();
        if (tokenRepository.existsById(jit)) throw new AppException(ErrorCode.UNAUTHENTICATED);
        return signedJWT;
    }

    @Override
    public IntrospectTokenResponse introspectTokenResponse(IntrospectTokenRequest introspectTokenRequest)
            throws JOSEException, ParseException {
        var token = introspectTokenRequest.getToken();
        // kiêm tra  token đúng hay ko
        boolean isValid = true;
        try {
            verifiedToken(token, false);
        } catch (AppException e) {
            isValid = false;
        }
        return IntrospectTokenResponse.builder().valid(isValid).build();
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshToken request) throws ParseException, JOSEException {
        var signToken = verifiedToken(request.getToken(), true);
        // B1 :  logout token cũ khi gần hết hàng
        String jit = signToken.getJWTClaimsSet().getJWTID();
        Date extryTime = signToken.getJWTClaimsSet().getExpirationTime();
        InvalidatedToken tokenTime = new InvalidatedToken();
        tokenTime.setId(jit);
        tokenTime.setExpiryTime(extryTime);
        tokenRepository.save(tokenTime);

        // B2 : refresh token mới theo user
        // get user
        var username = signToken.getJWTClaimsSet().getSubject();
        UserEntity user = Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));
        // tạo token nếu đăng nhập thành công
        var token = generateToken(user);
        // buider ở lombok
        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    public String generateToken(UserEntity user) {
        // tạo header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512); // chọn thuật toans
        // tạo payload
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("vanmanh.com") // domain
                .issueTime(new Date()) // thời gian
                .expirationTime(
                        new Date( // thời gian hết hạn
                                Instant.now()
                                        .plus(VALID_DURATION, ChronoUnit.SECONDS)
                                        .toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user)) // để có thể autherization cần role
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        // build thông tin token
        JWSObject jwsObject = new JWSObject(header, payload);
        // ký token
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes())); // ký bằng các thuật toán đc chọn
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Not define J TOKEN");
        }
    }

    // tạo scope từ user
    private String buildScope(UserEntity user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            for (RoleEntity item : user.getRoles()) {
                stringJoiner.add("ROLE_" + item.getName());
                if (!CollectionUtils.isEmpty(item.getPermissions())) {
                    // add thêm permission thao tác cảu role
                    item.getPermissions().forEach(permissionEntity -> stringJoiner.add(permissionEntity.getName()));
                }
            }
        }
        return stringJoiner.toString();
    }
}
