package com.mahmoud.appointmentsystem.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class AuthInterceptor implements RequestInterceptor {
    private final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
    @Override
    public void apply(RequestTemplate requestTemplate) {

        //String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String authHeader = attributes.getRequest().getHeader("Authorization");

        if (authHeader != null && !authHeader.isEmpty()) {
            logger.info("authHeader is {}", authHeader);
            requestTemplate.header("Authorization", authHeader);
        }
        else {
            logger.error("No token found");
        }
    }
}
