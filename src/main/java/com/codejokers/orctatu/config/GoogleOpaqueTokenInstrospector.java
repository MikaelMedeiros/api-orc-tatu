package com.codejokers.orctatu.config;

import com.codejokers.orctatu.dto.TokenInfoDTO;
import com.codejokers.orctatu.dto.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
public class GoogleOpaqueTokenInstrospector implements OpaqueTokenIntrospector {

    private final WebClient userInfoClient;

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("token", token);

        UserInfoDTO user =  userInfoClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth2/v3/userinfo")
                        .queryParam("access_token", token)
                        .build())
                .retrieve()
                .bodyToMono(UserInfoDTO.class)
                .block();
        System.out.println(user.toString());

        attributes.put("sub", user.sub());
        attributes.put("name", user.name());
        attributes.put("picture", user.picture());

        var tokenInfo =  userInfoClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth2/v3/tokeninfo")
                        .queryParam("access_token", token)
                        .build())
                .retrieve()
                .bodyToMono(TokenInfoDTO.class)
                .block();

        attributes.put("expiration", tokenInfo.exp());


        return new OAuth2IntrospectionAuthenticatedPrincipal(user.name(), attributes, null);
    }
}
