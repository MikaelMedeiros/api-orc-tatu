package com.codejokers.orctatu.controller;

import com.codejokers.orctatu.dto.BudgetDTO;
import com.codejokers.orctatu.entity.Budget;
import com.codejokers.orctatu.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "budgets", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    ResponseEntity<Budget> save(@RequestBody @Valid final BudgetDTO budgetDTO, @AuthenticationPrincipal final OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal) {
        final Budget budget = budgetService.save(budgetDTO, oAuth2AuthenticatedPrincipal);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(budget.getId()).toUri()).body(budget);
    }
}