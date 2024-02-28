package com.codejokers.orctatu.config;

import com.codejokers.orctatu.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
class GoogleOpaqueTokenInstrospector implements OpaqueTokenIntrospector {

    private final String instrospectUri;
    private final RestClient restClient;

    public GoogleOpaqueTokenInstrospector(@Value("${spring.security.oauth2.resourceserver.opaque-token.instrospect-uri}") final String instrospectUri) {
        this.instrospectUri = instrospectUri;
        this.restClient = RestClient.create();
    }

    @Override
    @Cacheable(value="instrospect-user", key="#accessToken")
    public OAuth2AuthenticatedPrincipal introspect(final String accessToken) {

        final UserDTO userDTO = restClient.get()
                                          .uri("%s/userinfo?access_token=%s".formatted(instrospectUri, accessToken))
                                          .accept(MediaType.APPLICATION_JSON)
                                          .retrieve()
                                          .body(UserDTO.class);

        final Map<String, Object> attributes = new HashMap<>();
        attributes.put("userDTO", userDTO);
        return new OAuth2IntrospectionAuthenticatedPrincipal(attributes, List.of(new SimpleGrantedAuthority("USER")));
    }

    @Scheduled(fixedRate = 3600000)
    @CacheEvict(value = {"instrospect-user", "authentication-url"}, allEntries = true)
    public void clearApplicationCache() {
        log.info("Limpando cache da aplicação. Hora: {}", LocalDateTime.now());
    }
}