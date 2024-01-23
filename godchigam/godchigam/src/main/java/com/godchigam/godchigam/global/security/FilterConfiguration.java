package com.godchigam.godchigam.global.security;

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

    @Bean
    public FilterRegistrationBean<JwtDecoderFilter> registerJwtFilter() {
        FilterRegistrationBean<JwtDecoderFilter> registerationBean = new FilterRegistrationBean<>(new JwtDecoderFilter(jwtTokenProvider));
        registerationBean.addUrlPatterns("/*");
        registerationBean.setOrder(0);
        return registerationBean;
    }
}
