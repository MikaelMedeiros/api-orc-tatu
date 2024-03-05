package com.codejokers.orctatu.controller;

import com.codejokers.orctatu.dto.BudgetDTO;
import com.codejokers.orctatu.dto.BudgetResponseDTO;
import com.codejokers.orctatu.dto.UpdateBudgetDTO;
import com.codejokers.orctatu.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/budgets", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    ResponseEntity<BudgetResponseDTO> save(@RequestBody @Valid final BudgetDTO budgetDTO, @AuthenticationPrincipal final OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal) {
        final BudgetResponseDTO budgetResponseDTO = budgetService.save(budgetDTO, oAuth2AuthenticatedPrincipal);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(budgetResponseDTO.id()).toUri()).body(budgetResponseDTO);
    }

    @GetMapping("{id}")
    ResponseEntity<BudgetResponseDTO> find(@PathVariable final Long id) {
        return ResponseEntity.ok(budgetService.find(id));
    }

    @GetMapping
    ResponseEntity<List<BudgetResponseDTO>> findAll(@AuthenticationPrincipal final OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal) {
        return ResponseEntity.ok(budgetService.findAll(oAuth2AuthenticatedPrincipal));
    }

    @PutMapping("{id}")
    ResponseEntity<BudgetResponseDTO> update(@PathVariable final Long id, @RequestBody @Valid final UpdateBudgetDTO updateBudgetDTO) {
        return ResponseEntity.ok(budgetService.update(id, updateBudgetDTO));
    }
}