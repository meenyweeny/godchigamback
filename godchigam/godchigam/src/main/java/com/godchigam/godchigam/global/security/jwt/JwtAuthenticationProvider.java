package com.godchigam.godchigam.global.security.jwt;

import com.godchigam.godchigam.global.security.UserDetail;
import com.godchigam.godchigam.global.security.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailService userDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("JwtAuthenticationProvider authenticate");
        System.out.println("GET PRINCIPAL ! -> " + authentication.getPrincipal());
        // 여기서 유저를 인증한다
        String username = (String) authentication.getPrincipal();
        UserDetail userDetail = (UserDetail) userDetailService.loadUserByUsername(username);

        return new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return userDetail;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return null;
            }
        };
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }

//    @Override
//    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//        System.out.println("DAO additionalAuthenticationChecks CALLED");
//        super.additionalAuthenticationChecks(userDetails, authentication);
//    }
}
