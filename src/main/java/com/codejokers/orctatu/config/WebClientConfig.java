package com.codejokers.orctatu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientConfig {

    @Value("${spring.security.oauth2.resourceserver.opaque-token.instrospect-uri}")
    private String instrospectUri;

    @Bean
    public WebClient userInfoClient() {
        return WebClient.builder().baseUrl(instrospectUri).build();
    }
}
