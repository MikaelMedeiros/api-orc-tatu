package com.codejokers.orctatu.services;

import com.codejokers.orctatu.dtos.BudgetDto;
import com.codejokers.orctatu.mapper.BudgetMapper;
import com.codejokers.orctatu.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;

    public void saveBudget(BudgetDto budgetDto, Object userId) {
        var budget = budgetMapper.toBudget(budgetDto);
        budgetRepository.save(budget);
    }
}