package com.codejokers.orctatu.controllers;

import io.swagger.v3.oas.annotations.headers.Header;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BudgetController {

    @GetMapping("/budget")
    public List<String> getBudgets() {
        return List.of("Borboleta", "Elfo", "Árvore", "céu", "azull");
    }

    @GetMapping("/budget/config")
    public String configureBudget() {
        return "configurando...";
    }
}
