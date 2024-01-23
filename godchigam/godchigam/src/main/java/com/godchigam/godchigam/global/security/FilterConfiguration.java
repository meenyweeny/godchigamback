package com.godchigam.godchigam.global.security;

import com.godchigam.godchigam.global.security.jwt.JwtDecoderFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean<JwtDecoderFilter> registerJwtFilter() {
        FilterRegistrationBean<JwtDecoderFilter> registerationBean = new FilterRegistrationBean<>(new JwtDecoderFilter());
        registerationBean.addUrlPatterns("/*");
        registerationBean.setOrder(0);
        return registerationBean;
    }
}
