package com.codejokers.orctatu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Profile("default")
@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http.cors(Customizer.withDefaults())
                   .exceptionHandling(custom -> custom.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                   .sessionManagement(customizerSession -> customizerSession.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                   .authorizeHttpRequests(authorize -> {
                       authorize.requestMatchers(HttpMethod.GET, "/authentication/**", "/h2-console/**").permitAll();
                       authorize.requestMatchers(HttpMethod.POST, "/h2-console/**").permitAll();
                       authorize.anyRequest().authenticated();
                   })
                   .oauth2ResourceServer(coustomizerResource -> coustomizerResource.opaqueToken(Customizer.withDefaults()))
                   .build();
    }
}