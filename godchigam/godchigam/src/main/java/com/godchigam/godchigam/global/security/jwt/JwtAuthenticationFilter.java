package com.godchigam.godchigam.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.godchigam.godchigam.domain.auth.dto.UserLoginRequest;
import com.godchigam.godchigam.domain.auth.entity.User;
import com.godchigam.godchigam.global.jwt.JwtTokenProvider;
import com.godchigam.godchigam.global.security.UserDetail;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtAuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtAuthenticationManager jwtAuthenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = jwtAuthenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        setFilterProcessesUrl("/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        System.out.println("==========================================");
        System.out.println(this.getClass().getCanonicalName() + "attemptAuthentication() called");
        System.out.println("JWT Authentication Filter : trying Login");

        ObjectMapper om = new ObjectMapper();
        UserLoginRequest userLoginRequest;
        try {
            userLoginRequest = om.readValue(request.getInputStream(), UserLoginRequest.class);
            System.out.println("USER LOGIN REQUEST INFO -> " + userLoginRequest.toString());
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            System.out.println("is authentication null? -> " + (authentication==null));
            System.out.println("attemptAuthentication ended\n========================================");
            return authentication;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        UserDetail info = (UserDetail) authResult.getPrincipal();
        User loginUser = info.getUser();
        System.out.println("Authentication SUCCESS!!! \n USER " + info.getUser().toString());
//        System.out.println("USERNAME " + info.getUsername());
//        System.out.println("PASSWORD " + info.getPassword());

//        userLoginRequest = om.readValue(request.getInputStream(), UserLoginRequest.class);
        String accessToken = jwtTokenProvider.generateAccessToken(loginUser.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(loginUser.getEmail());
        Long userId = 123L;
        // 원하는 응답 내용 설정
//        response.getWriter().write(refreshToken);
        Map<String, Object> userInfo = new LinkedHashMap<>();
        userInfo.put("userId",loginUser.getUserId());
        userInfo.put("accessToken",accessToken);
        userInfo.put("refreshToken",refreshToken);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
//        // JSON 응답을 생성
        String jsonResponse = new ObjectMapper().writeValueAsString(userInfo);
        response.getWriter().write(jsonResponse);
        // 응답을 보내고 메서드를 종료
    }
}
