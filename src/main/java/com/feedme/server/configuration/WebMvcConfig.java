package com.feedme.server.configuration;

import com.feedme.server.filters.TokenValidatorFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    TokenValidatorFilter tokenValidatorFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenValidatorFilter)
                .addPathPatterns("/feeders")
                .addPathPatterns("/feeders/*")
                .addPathPatterns("/pets/*")
                .addPathPatterns("/feeders/*");
    }
}