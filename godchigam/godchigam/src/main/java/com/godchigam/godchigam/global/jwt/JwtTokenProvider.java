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
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    public enum TokenType {
        Access, Refresh
    };

    @Value("${jwt.secret}")
    private String secretKey;

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
