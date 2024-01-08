package com.codejokers.orctatu.controllers;

import com.codejokers.orctatu.dtos.BudgetDto;
import com.codejokers.orctatu.dtos.UserInfo;
import com.codejokers.orctatu.entities.Usuario;
import com.codejokers.orctatu.services.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class BudgetController {

    private final BudgetService budgetService;

    @GetMapping("/budgets")
    public List<String> getBudgets() {
        return List.of("Borboleta", "Elfo", "Árvore", "céu", "azull");
    }

    @PostMapping("/budgets")
    public ResponseEntity<Object> saveBudget(@Valid @RequestBody BudgetDto budgetDto,
                                             @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {

        var userId = principal.getAttributes().get("sub");
        budgetDto.setUsuario(new Usuario(userId.toString()));
        budgetService.saveBudget(budgetDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/budget/config")
    public String configureBudget() {
        return "configurando...";
    }
}
