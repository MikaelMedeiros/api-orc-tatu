package com.codejokers.orctatu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class WebConfig {

    private final String frontEndUrl;

    public WebConfig(@Value("${front-end.url}") final String frontEndUrl) {
        this.frontEndUrl = frontEndUrl;
    }

    @Bean
    WebMvcConfigurer corsConfig() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(frontEndUrl)
                        .allowedMethods(HttpMethod.GET.name(),
                                        HttpMethod.POST.name(),
                                        HttpMethod.PUT.name())
                        .allowedHeaders(HttpHeaders.AUTHORIZATION,
                                        HttpHeaders.CONTENT_TYPE);
            }
        };
    }
}