package com.godchigam.godchigam.global.security;

import com.godchigam.godchigam.global.jwt.JwtTokenProvider;
import com.godchigam.godchigam.global.security.jwt.JwtAuthenticationFilter;
import com.godchigam.godchigam.global.security.jwt.JwtAuthenticationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationManager authenticationManager;

    @Value("${jwt.secret}")
    String secretKey;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtTokenProvider tokenProvider() {
        return new JwtTokenProvider(secretKey);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(configurer ->
                        configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(new CorsFilterConfiguration().corsFilter())
                .addFilter(new JwtAuthenticationFilter(authenticationManager, tokenProvider()))
                .formLogin(configurer -> configurer.disable())
                .httpBasic(configurer -> configurer.disable())
                .authorizeHttpRequests(request ->
                        request.anyRequest().permitAll());
        return http.build();
    }
}
