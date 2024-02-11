package com.codejokers.orctatu.controller;

import com.codejokers.orctatu.dto.RefreshTokenInfoDTO;
import com.codejokers.orctatu.dto.UrlDTO;
import com.codejokers.orctatu.dto.UserInfoDTO;
import com.codejokers.orctatu.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "authentication", produces = MediaType.APPLICATION_JSON_VALUE)
class AuthenticationController {

    private final AuthenticationService authenticationService;

    @GetMapping("url")
    ResponseEntity<UrlDTO> getGoogleAuthenticationUrl() {
        return ResponseEntity.ok(authenticationService.getGoogleAuthenticationUrl());
    }

    @GetMapping("callback")
    ResponseEntity<UserInfoDTO> callbackGoogle(@RequestParam("code") final String code) {
        return ResponseEntity.ok(authenticationService.callbackGoogle(code));
    }

    @GetMapping("refresh-token")
    ResponseEntity<RefreshTokenInfoDTO> getNewAccessToken(@RequestParam("refresh-token") final String refreshToken) {
        return ResponseEntity.ok(authenticationService.getNewAccessToken(refreshToken));
    }
}