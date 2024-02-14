package com.codejokers.orctatu.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

@Configuration
@EnableWebSecurity
class SecurityConfig {

    private final AuthenticationEntryPoint authenticationEntryPoint;

    public SecurityConfig(@Qualifier("delegatedAuthenticationEntryPoint") final AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http.cors(Customizer.withDefaults())
                   .headers(headers ->
                       headers.xssProtection(xss ->
                           xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
                       ).contentSecurityPolicy(cps ->
                           cps.policyDirectives("script-src 'self'")
                       ))
                   .exceptionHandling(customExceptionHandling -> customExceptionHandling.authenticationEntryPoint(authenticationEntryPoint))
                   .sessionManagement(customizerSession -> customizerSession.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                   .authorizeHttpRequests(authorize -> {
                       authorize.requestMatchers(HttpMethod.GET, "/authentication/**").permitAll();
                       authorize.anyRequest().authenticated();
                   })
                   .oauth2ResourceServer(coustomizerResource -> coustomizerResource.opaqueToken(Customizer.withDefaults()))
                   .build();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/h2-console/**");
    }
}