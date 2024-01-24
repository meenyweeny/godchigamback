package com.godchigam.godchigam.global.security;

import com.godchigam.godchigam.domain.auth.repository.UserRepository;
import com.godchigam.godchigam.global.jwt.JwtTokenProvider;
import com.godchigam.godchigam.global.security.jwt.JwtDecoderFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfiguration {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Bean
    public FilterRegistrationBean<JwtDecoderFilter> registerJwtFilter() {
        FilterRegistrationBean<JwtDecoderFilter> registerationBean =
                new FilterRegistrationBean<>(new JwtDecoderFilter(jwtTokenProvider, userRepository));
        registerationBean.addUrlPatterns("/*");
        registerationBean.setOrder(0);
        return registerationBean;
    }
}
