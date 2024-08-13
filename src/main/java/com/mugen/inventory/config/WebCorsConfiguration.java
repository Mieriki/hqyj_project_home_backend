package com.mugen.inventory.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

//@Configuration
public class WebCorsConfiguration {
    @Value("${spring.web.cors.origin}")
    List<String> origins;

    @Value("${spring.web.cors.header}")
    List<String> headers;

    @Value("${spring.web.cors.method}")
    List<String> methods;

    @Value("${spring.web.cors.exposed}")
    List<String> exposed;


    // 当前跨域请求最大有效时长为1天
    private static final long MAX_AGE = 24 * 60 * 60;

    /**
     * 跨域配置
     */
//    @Bean
//    @Order(-200)
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOriginPatterns(origins);
        corsConfiguration.setAllowedHeaders(headers);
        corsConfiguration.setAllowedMethods(methods);
        corsConfiguration.setExposedHeaders(exposed);
        corsConfiguration.setMaxAge(MAX_AGE);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }
}
