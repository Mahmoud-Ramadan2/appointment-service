package com.mahmoud.appointmentsystem.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTUtil {
    @Value("${JWTSecretKey}")
    private String JWTSecretKey;

    public String retrieveUsername(String token) {
        DecodedJWT decodedJWT = validateToken(token);
        return decodedJWT.getClaim("username").asString();
    }

    public List<GrantedAuthority> getRoles(String token) {
        DecodedJWT decodedJWT = validateToken(token);
        List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
        return roles.stream()
                .map(r-> new SimpleGrantedAuthority(r))
                .collect(Collectors.toList());
    }


    private DecodedJWT validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWTSecretKey))
                    .withSubject("User_Details")
                    .withIssuer("appointments/system")
                    .build();
            return verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Invalid or expired JWT token", e);
        }
    }

}
