package com.example.TelConnect.security;

import com.example.TelConnect.config.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import javax.crypto.SecretKey;
import java.security.Key;

@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

//    @Value("${app.jwt-secret}")
//    private String jwtSecret;
//
//    @Value("${app-jwt-expiration-milliseconds}")
//    private long jwtExpirationDate;

    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String generateToken(Authentication authentication){

        String name = authentication.getName();
        Date currentDate= new Date();

        Date expireDate= new Date(currentDate.getTime() + jwtProperties.getExpirationMilliseconds());

        return Jwts.builder()
                .setSubject(name)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret()));
    }

    public String getUserName(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token){
        Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parse(token);
        return true;
    }
}
