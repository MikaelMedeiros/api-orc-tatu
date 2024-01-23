package com.codejokers.orctatu.controller;

import com.codejokers.orctatu.dto.UrlDTO;
import com.codejokers.orctatu.dto.UserInfoDTO;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final String FRONTEND_URL = "http://localhost:4200";

    @Value("${spring.security.oauth2.resourceserver.opaque-token.clientId}")
    private String clientId;

    @Value("${spring.security.oauth2.resourceserver.opaque-token.clientSecret}")
    private String clientSecret;

    private final OpaqueTokenIntrospector opaqueTokenIntrospector;

    @GetMapping("/auth/url")
    public ResponseEntity<UrlDTO> auth() {
        var url = new GoogleAuthorizationCodeRequestUrl(
                clientId,
                FRONTEND_URL,
                Arrays.asList("email", "profile", "openid")
        ).build();
        return ResponseEntity.ok(new UrlDTO(url));
    }

    @GetMapping("/auth/callback")
    public ResponseEntity<UserInfoDTO> callback(@RequestParam("code") String code) throws URISyntaxException {

        String token;
        String sub ;
        String name;
        String image;
        String exp;
        try {
            token = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(), new GsonFactory(),
                    clientId,
                    clientSecret,
                    code,
                    "http://localhost:4200"
            ).execute().getAccessToken();
            var opaque = opaqueTokenIntrospector.introspect(token);
           sub = opaque.getAttribute("sub");
           name = opaque.getAttribute("name");
           image = opaque.getAttribute("picture");
           exp = opaque.getAttribute("expiration");

        } catch (IOException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(new UserInfoDTO(sub, name, image, token, exp));
    }



}
