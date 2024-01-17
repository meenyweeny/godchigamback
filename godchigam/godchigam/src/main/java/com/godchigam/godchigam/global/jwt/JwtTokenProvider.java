package com.godchigam.godchigam.global.jwt;

import io.jsonwebtoken.Claims;
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
public class JwtTokenProvider implements InitializingBean {

    @Value("${jwt.secret}")
    private String secretKey;

    public String generateToken(Long userId) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
        Date now = new Date();
        String jwt = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setExpiration(new Date(now.getTime() + Duration.ofDays(20).toMillis()))
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        System.out.println(jwt);
        return jwt;
    }

    public Long getUserId(String accessToken) {
        String userId = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody().getSubject();
        System.out.println("jwt parse " + userId);
        return Long.parseLong(userId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
