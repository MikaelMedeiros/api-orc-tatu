package com.codejokers.orctatu.config;

import com.codejokers.orctatu.dto.TokenInfoDTO;
import com.codejokers.orctatu.dto.UserInfoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Component
class GoogleOpaqueTokenInstrospector implements OpaqueTokenIntrospector {

    private final String instrospectUri;
    private final RestClient restClient;

    public GoogleOpaqueTokenInstrospector(@Value("${spring.security.oauth2.resourceserver.opaque-token.instrospect-uri}") final String instrospectUri) {
        this.instrospectUri = instrospectUri;
        this.restClient = RestClient.create();
    }

    @Override
    public OAuth2AuthenticatedPrincipal introspect(final String accessToken) {

        final UserInfoDTO userInfoDTO = restClient.get()
                                                  .uri("%s/userinfo?access_token=%s".formatted(instrospectUri, accessToken))
                                                  .accept(MediaType.APPLICATION_JSON)
                                                  .retrieve()
                                                  .body(UserInfoDTO.class);

        final TokenInfoDTO tokenInfoDTO = restClient.get()
                                                    .uri("%s/tokeninfo?access_token=%s".formatted(instrospectUri, accessToken))
                                                    .accept(MediaType.APPLICATION_JSON)
                                                    .retrieve()
                                                    .body(TokenInfoDTO.class);

        final Map<String, Object> attributes = new HashMap<>();
        if (userInfoDTO != null) userInfoDTO.setTokenInfoDTO(tokenInfoDTO);
        attributes.put("userInfoDTO", userInfoDTO);
        return new OAuth2IntrospectionAuthenticatedPrincipal(attributes, null);
    }
}