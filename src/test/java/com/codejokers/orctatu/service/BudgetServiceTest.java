package com.codejokers.orctatu.service;

import com.codejokers.orctatu.dto.BudgetDTO;
import com.codejokers.orctatu.entity.Budget;
import com.codejokers.orctatu.factory.BudgetDTOFactory;
import com.codejokers.orctatu.factory.BudgetFactory;
import com.codejokers.orctatu.factory.OAuth2AuthenticatedPrincipalImpl;
import com.codejokers.orctatu.mapper.BudgetMapper;
import com.codejokers.orctatu.repository.BudgetRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class BudgetServiceTest {

    @InjectMocks
    private BudgetService budgetService;
    @Mock
    private BudgetMapper budgetMapper;
    @Mock
    private BudgetRepository budgetRepository;
    private final OAuth2AuthenticatedPrincipalImpl oAuth2AuthenticatedPrincipalImpl = new OAuth2AuthenticatedPrincipalImpl();

    @Test
    void givenSave_whenInputDataIsCorrect_thenSaveAndReturnBudget() throws JsonProcessingException {

        final BudgetDTO budgetDTO = BudgetDTOFactory.createBudgetDTO();
        final Budget expectedBudget = BudgetFactory.createBudget();
        when(budgetMapper.toBudget(any(BudgetDTO.class))).thenReturn(expectedBudget);
        when(budgetRepository.save(any(Budget.class))).thenReturn(expectedBudget);
        final Budget budgetSaved = budgetService.save(budgetDTO, oAuth2AuthenticatedPrincipalImpl);

        verify(budgetMapper).toBudget(budgetDTO);
        verify(budgetRepository).save(expectedBudget);
        assertEquals(expectedBudget.getId(), budgetSaved.getId());
        assertEquals(expectedBudget.getGoogleId(), budgetSaved.getGoogleId());
        assertEquals(expectedBudget.getClientName(), budgetSaved.getClientName());
        assertEquals(expectedBudget.getDraw(), budgetSaved.getDraw());
        assertEquals(expectedBudget.getCentimeter(), budgetSaved.getCentimeter());
        assertEquals(expectedBudget.getPricePerCentimeter(), budgetSaved.getPricePerCentimeter());
        assertEquals(expectedBudget.getBodyLocals().size(), budgetSaved.getBodyLocals().size());
        assertEquals(expectedBudget.getStyles().size(), budgetSaved.getStyles().size());
        assertEquals(expectedBudget.getDetails().size(), budgetSaved.getDetails().size());
        assertEquals(expectedBudget.getDescription(), budgetSaved.getDescription());
        assertEquals(expectedBudget.getStudioPercentage(), budgetSaved.getStudioPercentage());
        assertEquals(expectedBudget.getParkingCost(), budgetSaved.getParkingCost());
        assertEquals(expectedBudget.getMaterialCost(), budgetSaved.getMaterialCost());
        assertEquals(expectedBudget.getCreditCardFee(), budgetSaved.getCreditCardFee());
        assertEquals(expectedBudget.getTattooValue(), budgetSaved.getTattooValue());
        assertEquals(expectedBudget.getStudioFee(), budgetSaved.getStudioFee());
        assertEquals(expectedBudget.getNetValue(), budgetSaved.getNetValue());
        assertEquals(expectedBudget.getGrossValue(), budgetSaved.getGrossValue());
        assertEquals(expectedBudget.getCreditCardValue(), budgetSaved.getCreditCardValue());
        assertEquals(expectedBudget.getStatus(), budgetSaved.getStatus());
        assertEquals(expectedBudget.getPaymentMethod(), budgetSaved.getPaymentMethod());
    }
}