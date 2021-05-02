package com.tak.soda.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.filter.OrderedCharacterEncodingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean encodingFilterBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
        filter.setForceEncoding(true);
        filter.setEncoding("MS949");
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/ms949filterUrl/*");
        return registrationBean;
    }
}
