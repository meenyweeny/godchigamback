package com.godchigam.godchigam.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Duration;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Configuration
//@RequiredArgsConstructor
public class JwtTokenProvider {

    public enum TokenType {
        Access, Refresh
    };

    private final String secretKey;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        this.secretKey = secretKey;
    }

    public String generateToken(TokenType tokenType, Long userId) {
        Date now = new Date();
        int duration = tokenType.equals(TokenType.Access) ? 1 : 20;
        Claims claims = Jwts.claims()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofDays(duration).toMillis()));
        String jwt = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return jwt;
    }

    public String generateAccessToken(String email) {
        System.out.println("SECRET KEY " + secretKey);
        Date now = new Date();
        Claims claims = Jwts.claims()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofDays(1).toMillis()));
        String jwt = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return jwt;
    }


    public String generateRefreshToken(String email) {
        Date now = new Date();
        Claims claims = Jwts.claims()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofDays(21).toMillis()));
        String jwt = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        System.out.println(jwt);
        return jwt;
    }

    public boolean isValidToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
            return claims.getExpiration().before(new Date());
        } catch(ExpiredJwtException expiredJwtException) {
            return false;
        }
    }

    public Long getUserId(String accessToken) {
        String userId = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody().getSubject();
        return Long.parseLong(userId);
    }
}
