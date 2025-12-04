package com.security.TryingToMakeCorrectRealizationOfJWT.jwt;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import com.auth0.jwt.JWT;

@Component
public class JWTService {
    @Value("${jwt-secret}")
    private String secret;

    public String generateToken(String email) throws IllegalArgumentException, JWTCreationException {

        String token = JWT.create()
                .withSubject(email)
                .withClaim("email", email)
                .withIssuer("MIQ")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000)) // 1 час
                .sign(Algorithm.HMAC256(secret));

        return token;
    }

    public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("MIQ")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        String email = jwt.getSubject();

        return email;
    }
}
