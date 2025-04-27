package com.mahmoud.appointmentsystem.client;

import feign.RequestInterceptor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
public RequestInterceptor bearerTokenRequestInterceptor(){
        return
               new AuthInterceptor();

    }
}
