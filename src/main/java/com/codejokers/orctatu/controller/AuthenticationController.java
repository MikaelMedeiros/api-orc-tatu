package com.codejokers.orctatu.controller;

import com.codejokers.orctatu.dto.RefreshTokenDTO;
import com.codejokers.orctatu.dto.UrlDTO;
import com.codejokers.orctatu.dto.UserResponseDTO;
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
@RequestMapping(value = "v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
class AuthenticationController {

    private final AuthenticationService authenticationService;

    @GetMapping("url")
    ResponseEntity<UrlDTO> getGoogleAuthenticationUrl() {
        return ResponseEntity.ok(authenticationService.getGoogleAuthenticationUrl());
    }

    @GetMapping("callback")
    ResponseEntity<UserResponseDTO> callbackGoogle(@RequestParam final String code) {
        return ResponseEntity.ok(authenticationService.callbackGoogle(code));
    }

    @GetMapping("refresh")
    ResponseEntity<RefreshTokenDTO> refresh(@RequestParam final String token) {
        return ResponseEntity.ok(authenticationService.refresh(token));
    }

    @GetMapping("revoke")
    ResponseEntity<Void> revoke(@RequestParam final String token) {
        authenticationService.revoke(token);
        return ResponseEntity.noContent().build();
    }
}