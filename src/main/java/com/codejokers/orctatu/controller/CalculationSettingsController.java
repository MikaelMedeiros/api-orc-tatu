package com.codejokers.orctatu.controller;

import com.codejokers.orctatu.dto.CalculationSettingsDTO;
import com.codejokers.orctatu.entity.CalculationSettings;
import com.codejokers.orctatu.service.CalculationSettingsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "calculation-settings", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
class CalculationSettingsController {

    private final CalculationSettingsService calculationSettingsService;

    @GetMapping
    ResponseEntity<CalculationSettings> find(@AuthenticationPrincipal final OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal) {
        return ResponseEntity.ok(calculationSettingsService.find(oAuth2AuthenticatedPrincipal));
    }

    @PostMapping
    ResponseEntity<CalculationSettings> save(@RequestBody @Valid final CalculationSettingsDTO calculationSettingsDTO, @AuthenticationPrincipal final OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal) {
        final CalculationSettings calculationSettings = calculationSettingsService.save(calculationSettingsDTO, oAuth2AuthenticatedPrincipal);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()).body(calculationSettings);
    }

    @PutMapping
    ResponseEntity<CalculationSettings> update(@RequestBody @Valid final CalculationSettingsDTO calculationSettingsDTO, @AuthenticationPrincipal final OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal) {
        return ResponseEntity.ok(calculationSettingsService.update(calculationSettingsDTO, oAuth2AuthenticatedPrincipal));
    }
}