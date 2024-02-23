package com.codejokers.orctatu.service;

import com.codejokers.orctatu.dto.BudgetDTO;
import com.codejokers.orctatu.dto.UpdateBudgetDTO;
import com.codejokers.orctatu.dto.UserInfoDTO;
import com.codejokers.orctatu.entity.Budget;
import com.codejokers.orctatu.enums.Status;
import com.codejokers.orctatu.exception.ApplicationException;
import com.codejokers.orctatu.mapper.BudgetMapper;
import com.codejokers.orctatu.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetMapper budgetMapper;
    private final BudgetRepository budgetRepository;

    public Budget save(final BudgetDTO budgetDTO, final OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal) {

        final Budget budget = budgetMapper.toBudget(budgetDTO);
        budget.setGoogleId(getSub(oAuth2AuthenticatedPrincipal));
        calculate(budget);
        return budgetRepository.save(budget);
    }

    public List<Budget> findAll(final OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal) {
        return budgetRepository.findAllByGoogleId(getSub(oAuth2AuthenticatedPrincipal));
    }

    public Budget update(final Long id, final UpdateBudgetDTO updateBudgetDTO) {

        final Optional<Budget> budget = budgetRepository.findById(id);
        if (budget.isEmpty()) throw new ApplicationException(404, "Orçamento não encontrado.");
        final Budget updatedBudget = budget.get();
        setFields(updateBudgetDTO, updatedBudget);
        return budgetRepository.save(updatedBudget);
    }

    private String getSub(final OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal) {
        final UserInfoDTO userInfoDTO = (UserInfoDTO) oAuth2AuthenticatedPrincipal.getAttributes().get("userInfoDTO");
        return userInfoDTO.getSub();
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

    private void setFields(final UpdateBudgetDTO updateBudgetDTO, final Budget updatedBudget) {

        if ((updateBudgetDTO.status() == Status.SCHEDULED_PAID && updateBudgetDTO.paymentMethod() == null) ||
            (updateBudgetDTO.status() == Status.DONE && updateBudgetDTO.paymentMethod() == null)) {
            throw new ApplicationException(400, "Preencha o método de pagamento.");
        }
        updatedBudget.setStatus(updateBudgetDTO.status());
        updatedBudget.setPaymentMethod(updateBudgetDTO.paymentMethod());
    }
}