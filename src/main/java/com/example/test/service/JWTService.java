package com.example.test.service;

import com.example.test.entity.UserEntity;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JWTService {
    private String secrekey = "xGwQ7JFVZuVHF6BFGlhGTr8dOfQRAbe9qpuStjIwZzTZuFod7d2NqVO0glxqaz4YHbHOliCHRAf2yX3Rk5c4Ce";
    public String generateAccessToken(UserEntity user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        Date issueTime = new Date();
        Date expirationTime = Date.from(issueTime.toInstant().plus(30, ChronoUnit.MINUTES));
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issueTime(issueTime)
                .expirationTime(expirationTime)
                .build();
        Payload payload = new Payload(claimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(secrekey));
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
        return jwsObject.serialize();
    }

    public String generateRefreshTokenToken(UserEntity user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        Date issueTime = new Date();
        Date expirationTime = Date.from(issueTime.toInstant().plus(30, ChronoUnit.DAYS));
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issueTime(issueTime)
                .expirationTime(expirationTime)
                .build();
        Payload payload = new Payload(claimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(secrekey));
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
        return jwsObject.serialize();
    }
}
