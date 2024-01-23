package com.codejokers.orctatu.service;

import com.codejokers.orctatu.dto.BudgetDTO;
import com.codejokers.orctatu.entity.Budget;
import com.codejokers.orctatu.mapper.BudgetMapper;
import com.codejokers.orctatu.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetMapper budgetMapper;
    private final BudgetRepository budgetRepository;

    public Budget save(final BudgetDTO budgetDTO) {
        return budgetRepository.save(budgetMapper.toBudget(budgetDTO));
    }
}