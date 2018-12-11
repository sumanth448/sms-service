package com.auzmor.smsservice.config;

import com.auzmor.smsservice.filters.AuthenticationFilter;
import com.auzmor.smsservice.service.AccountService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

public class WebMvcConfiguration implements WebMvcConfigurer {
    @Bean
    public FilterRegistrationBean authenticationFilter(AccountService accountService) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AuthenticationFilter(accountService));
        registration.addUrlPatterns("/auzmor/*");
        registration.setName("authenticationFilter");
        registration.setOrder(2);
        return registration;
    }
}
