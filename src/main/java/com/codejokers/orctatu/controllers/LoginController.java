package com.codejokers.orctatu.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {

    @GetMapping("/autenticar")
    public Map<String, Object> login(@AuthenticationPrincipal OidcUser principal) {


        return Map.of(
                "id",  "XPLWSps7841saX",
                "name", "Mikaelson",
                "picture", "http://picturegoogle.com/mikaelson",
                "token", principal.getIdToken().getTokenValue()
        );
    }

}
