package com.codejokers.orctatu.service;

import com.codejokers.orctatu.dto.BudgetDTO;
import com.codejokers.orctatu.dto.BudgetResponseDTO;
import com.codejokers.orctatu.dto.UpdateBudgetDTO;
import com.codejokers.orctatu.dto.UserDTO;
import com.codejokers.orctatu.entity.Budget;
import com.codejokers.orctatu.entity.User;
import com.codejokers.orctatu.enums.Status;
import com.codejokers.orctatu.exception.ApplicationException;
import com.codejokers.orctatu.mapper.BudgetMapper;
import com.codejokers.orctatu.repository.BudgetRepository;
import com.codejokers.orctatu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetMapper budgetMapper;
    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    public BudgetResponseDTO save(final BudgetDTO budgetDTO, final OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal) {

        final Budget budget = budgetMapper.toBudget(budgetDTO);
        budget.setUser(getUser(oAuth2AuthenticatedPrincipal));
        calculate(budget);
        final Budget savedBudget = budgetRepository.save(budget);
        return budgetMapper.toBudgetResponseDTO(savedBudget);
    }

    public BudgetResponseDTO find(final Long id) {
        return budgetMapper.toBudgetResponseDTO(getBudget(id));
    }

    public List<BudgetResponseDTO> findAll(final OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal) {

        final List<Budget> budgets = budgetRepository.findAllByUser(getUser(oAuth2AuthenticatedPrincipal));
        return budgets.stream().map(budgetMapper::toBudgetResponseDTO).toList();
    }

    public BudgetResponseDTO update(final Long id, final UpdateBudgetDTO updateBudgetDTO) {

        final Budget budgetToBeUpdated = getBudget(id);
        setFields(updateBudgetDTO, budgetToBeUpdated);
        final Budget updatedBudget = budgetRepository.save(budgetToBeUpdated);
        return budgetMapper.toBudgetResponseDTO(updatedBudget);
    }

    private User getUser(final OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal) {

        final UserDTO userDTO = (UserDTO) oAuth2AuthenticatedPrincipal.getAttributes().get("userDTO");
        return userRepository.findByGoogleId(userDTO.getSub()).orElseThrow(() -> new ApplicationException(404, "Usuário não encontrado."));
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

    private Budget getBudget(final Long id) {
        return budgetRepository.findById(id).orElseThrow(() -> new ApplicationException(404, "Orçamento não encontrado."));
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