package com.codejokers.orctatu.service;

import com.codejokers.orctatu.dto.RefreshTokenInfoDTO;
import com.codejokers.orctatu.dto.UrlDTO;
import com.codejokers.orctatu.dto.UserInfoDTO;
import com.codejokers.orctatu.exception.ApplicationException;
import com.codejokers.orctatu.mapper.GoogleTokenResponseMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.CalendarScopes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Service
public class AuthenticationService {

    private final String clientId;
    private final String clientSecret;
    private final String frontEndUrl;
    private final String googleAuthorizationCodeRequestUrl;
    private final OpaqueTokenIntrospector opaqueTokenIntrospector;
    private final GoogleTokenResponseMapper googleTokenResponseMapper;

    public AuthenticationService(@Value("${spring.security.oauth2.resourceserver.opaque-token.clientId}") final String clientId,
                                 @Value("${spring.security.oauth2.resourceserver.opaque-token.clientSecret}") final String clientSecret,
                                 @Value("${front-end.url}") final String frontEndUrl,
                                 @Value("${google.authorization-code-request-url}") final String googleAuthorizationCodeRequestUrl,
                                 final OpaqueTokenIntrospector opaqueTokenIntrospector,
                                 final GoogleTokenResponseMapper googleTokenResponseMapper) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.frontEndUrl = frontEndUrl;
        this.googleAuthorizationCodeRequestUrl = googleAuthorizationCodeRequestUrl;
        this.opaqueTokenIntrospector = opaqueTokenIntrospector;
        this.googleTokenResponseMapper = googleTokenResponseMapper;
    }

    @Cacheable("authentication-url")
    public UrlDTO getGoogleAuthenticationUrl() {
        final String googleAuthenticationUrl = new GoogleAuthorizationCodeRequestUrl(googleAuthorizationCodeRequestUrl, clientId, frontEndUrl, Arrays.asList("email", "profile", "openid", CalendarScopes.CALENDAR)).build();
        return new UrlDTO(googleAuthenticationUrl);
    }

    public UserInfoDTO callbackGoogle(final String code) {

        final UserInfoDTO userInfoDTO;
        try {
            final GoogleTokenResponse googleTokenResponse = new GoogleAuthorizationCodeTokenRequest(new NetHttpTransport(),
                                                                                                    new GsonFactory(),
                                                                                                    clientId,
                                                                                                    clientSecret,
                                                                                                    code,
                                                                                                    frontEndUrl)
                                                                                                    .execute();

            final OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal = opaqueTokenIntrospector.introspect(googleTokenResponse.getAccessToken());
            userInfoDTO = oAuth2AuthenticatedPrincipal.getAttribute("userInfoDTO");
            if (userInfoDTO != null) {
                userInfoDTO.getTokenInfoDTO().setIdToken(googleTokenResponse.getIdToken());
                userInfoDTO.getTokenInfoDTO().setAccessToken(googleTokenResponse.getAccessToken());
                userInfoDTO.getTokenInfoDTO().setTokenType(googleTokenResponse.getTokenType());
                userInfoDTO.getTokenInfoDTO().setRefreshToken(googleTokenResponse.getRefreshToken());
            }
        } catch (final IOException exception) {
            log.error("Erro na autenticação do usuário: {}", exception.getMessage());
            throw new ApplicationException(401, "Erro ao autenticar o usuário.");
        }
        return userInfoDTO;
    }

    public RefreshTokenInfoDTO getNewAccessToken(final String refreshToken) {

        final GoogleTokenResponse googleTokenResponse;
        try {
            googleTokenResponse = new GoogleRefreshTokenRequest(new NetHttpTransport(),
                                                                new GsonFactory(),
                                                                refreshToken,
                                                                clientId,
                                                                clientSecret)
                                                                .execute();
        } catch (final IOException exception) {
            log.error("Erro ao tentar obter um novo token de acesso: {}", exception.getMessage());
            throw new ApplicationException(401, "Erro ao tentar obter um novo token de acesso.");
        }
        return googleTokenResponseMapper.toRefreshTokenInfoDTO(googleTokenResponse);
    }
}