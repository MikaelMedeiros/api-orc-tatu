package com.codejokers.orctatu.service;

import com.codejokers.orctatu.dto.BudgetDTO;
import com.codejokers.orctatu.entity.Budget;
import com.codejokers.orctatu.mapper.BudgetMapper;
import com.codejokers.orctatu.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetMapper budgetMapper;
    private final BudgetRepository budgetRepository;

    public Budget save(final BudgetDTO budgetDTO) {

        final Budget budget = budgetMapper.toBudget(budgetDTO);
        calculate(budget);
        return budgetRepository.save(budget);
    }

    private void calculate(final Budget budget) {

        final BigDecimal tattooValue = budget.getPricePerCentimeter().multiply(BigDecimal.valueOf(budget.getCentimeter()));
        budget.setTattooValue(tattooValue);

        final BigDecimal studioFee = tattooValue.multiply(budget.getStudioPercentage()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN);
        budget.setStudioFee(studioFee);
        budget.setNetValue(tattooValue.subtract(studioFee));

        final BigDecimal grossValue = tattooValue.add(budget.getParkingCost()).add(budget.getMaterialCost());
        budget.setGrossValue(grossValue);
        budget.setCreditCardValue(grossValue.add(budget.getCreditCardFee()));
    }
}