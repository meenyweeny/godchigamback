package com.godchigam.godchigam.global.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements AuthenticationManager {

//    private final JwtAuthenticationProvider jwtAuthenticationProvider;

//    private final DaoAuthenticationProvider jwtAuthenticationProvider;

    private final AuthenticationProvider jwtAuthenticationProvider;

    @Override
    public Authentication authenticate(
            Authentication authentication) throws AuthenticationException {
        System.out.println("JwtAuthenticationManager AUTHENTICATE ");
        return jwtAuthenticationProvider.authenticate(authentication);
    }
}
