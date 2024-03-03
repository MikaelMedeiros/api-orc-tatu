package com.codejokers.orctatu.service;

import com.codejokers.orctatu.dto.RefreshTokenDTO;
import com.codejokers.orctatu.dto.UrlDTO;
import com.codejokers.orctatu.dto.UserDTO;
import com.codejokers.orctatu.dto.UserResponseDTO;
import com.codejokers.orctatu.entity.User;
import com.codejokers.orctatu.exception.ApplicationException;
import com.codejokers.orctatu.mapper.GoogleTokenResponseMapper;
import com.codejokers.orctatu.mapper.UserMapper;
import com.codejokers.orctatu.repository.UserRepository;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthenticationService {

    private final String clientId;
    private final String clientSecret;
    private final String frontEndUrl;
    private final String googleAuthorizationCodeRequestUrl;
    private final OpaqueTokenIntrospector opaqueTokenIntrospector;
    private final GoogleTokenResponseMapper googleTokenResponseMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AuthenticationService(@Value("${spring.security.oauth2.resourceserver.opaque-token.clientId}") final String clientId,
                                 @Value("${spring.security.oauth2.resourceserver.opaque-token.clientSecret}") final String clientSecret,
                                 @Value("${front-end.url}") final String frontEndUrl,
                                 @Value("${google.authorization-code-request-url}") final String googleAuthorizationCodeRequestUrl,
                                 final OpaqueTokenIntrospector opaqueTokenIntrospector,
                                 final GoogleTokenResponseMapper googleTokenResponseMapper,
                                 final UserRepository userRepository,
                                 final UserMapper userMapper) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.frontEndUrl = frontEndUrl;
        this.googleAuthorizationCodeRequestUrl = googleAuthorizationCodeRequestUrl;
        this.opaqueTokenIntrospector = opaqueTokenIntrospector;
        this.googleTokenResponseMapper = googleTokenResponseMapper;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Cacheable("authentication-url")
    public UrlDTO getGoogleAuthenticationUrl() {
        final String googleAuthenticationUrl = new GoogleAuthorizationCodeRequestUrl(googleAuthorizationCodeRequestUrl,
                                                                                     clientId,
                                                                                     frontEndUrl,
                                                                                     Arrays.asList(OidcScopes.EMAIL,
                                                                                                   OidcScopes.OPENID,
                                                                                                   OidcScopes.PROFILE,
                                                                                                   CalendarScopes.CALENDAR))
                                                                                     .build();
        return new UrlDTO(googleAuthenticationUrl);
    }

    public UserResponseDTO callbackGoogle(final String code) {

        final UserDTO userDTO;
        try {
            final GoogleTokenResponse googleTokenResponse = callGoogleAuthorizationServer(code);
            userDTO = getUserInformation(googleTokenResponse);
            if (userDTO != null) {
                setTokenInformation(userDTO, googleTokenResponse);
                save(userDTO);
            }
        } catch (final IOException exception) {
            log.error("Erro na autenticação do usuário: {}", exception.getMessage());
            throw new ApplicationException(401, "Erro ao autenticar o usuário.");
        }
        return userMapper.toUserResponseDTO(userDTO);
    }

    public RefreshTokenDTO getNewAccessToken(final String refreshToken) {

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
        return googleTokenResponseMapper.toRefreshTokenDTO(googleTokenResponse);
    }

    private GoogleTokenResponse callGoogleAuthorizationServer(final String code) throws IOException {

        return new GoogleAuthorizationCodeTokenRequest(new NetHttpTransport(),
                                                       new GsonFactory(),
                                                       clientId,
                                                       clientSecret,
                                                       code,
                                                       frontEndUrl)
                                                       .execute();
    }

    private UserDTO getUserInformation(final GoogleTokenResponse googleTokenResponse) {

        final UserDTO userDTO;
        final OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal = opaqueTokenIntrospector.introspect(googleTokenResponse.getAccessToken());
        userDTO = oAuth2AuthenticatedPrincipal.getAttribute("userDTO");
        if (userDTO != null) {
            final String authorities = oAuth2AuthenticatedPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
            userDTO.setAuthorities(authorities);
        }
        return userDTO;
    }

    private void setTokenInformation(final UserDTO userDTO, final GoogleTokenResponse googleTokenResponse) {

        userDTO.setAccessToken(googleTokenResponse.getAccessToken());
        userDTO.setTokenType(googleTokenResponse.getTokenType());
        userDTO.setRefreshToken(googleTokenResponse.getRefreshToken());
        userDTO.setExpiration(getExpiretionInMilliSeconds(googleTokenResponse.getExpiresInSeconds()));
    }
    private Long getExpiretionInMilliSeconds(final long expiresInSeconds) {
        LocalDateTime expirationDateTime = LocalDateTime.now().plusSeconds(expiresInSeconds);
        return expirationDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    private void save(final UserDTO userDTO) {

        final Optional<User> optionalUser = userRepository.findByGoogleId(userDTO.getSub());
        if (optionalUser.isEmpty()) userRepository.save(userMapper.toUser(userDTO));
    }
}