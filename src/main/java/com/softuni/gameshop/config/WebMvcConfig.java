package com.softuni.gameshop.config;

import com.softuni.gameshop.config.interceptors.UsernameInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private UsernameInterceptor usernameInterceptor;

    public WebMvcConfig(UsernameInterceptor usernameInterceptor) {
        this.usernameInterceptor = usernameInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(usernameInterceptor).addPathPatterns("/", "/order");
    }
}
