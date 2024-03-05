package com.codejokers.orctatu.service;

import com.codejokers.orctatu.dto.BudgetDTO;
import com.codejokers.orctatu.dto.BudgetResponseDTO;
import com.codejokers.orctatu.entity.Budget;
import com.codejokers.orctatu.entity.User;
import com.codejokers.orctatu.exception.ApplicationException;
import com.codejokers.orctatu.factory.BudgetDTOFactory;
import com.codejokers.orctatu.factory.BudgetFactory;
import com.codejokers.orctatu.factory.BudgetResponseDTOFactory;
import com.codejokers.orctatu.factory.OAuth2AuthenticatedPrincipalImpl;
import com.codejokers.orctatu.factory.UpdateBudgetDTOFactory;
import com.codejokers.orctatu.factory.UserFactory;
import com.codejokers.orctatu.mapper.BudgetMapper;
import com.codejokers.orctatu.repository.BudgetRepository;
import com.codejokers.orctatu.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
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
    @Mock
    private UserRepository userRepository;
    private final OAuth2AuthenticatedPrincipalImpl oAuth2AuthenticatedPrincipalImpl = new OAuth2AuthenticatedPrincipalImpl();

    @Test
    void givenSave_whenInputDataIsCorrect_thenSaveAndReturnBudget() throws JsonProcessingException {

        final BudgetDTO budgetDTO = BudgetDTOFactory.createBudgetDTO();
        final Budget budget = BudgetFactory.createBudget();
        final BudgetResponseDTO expectedBudget = BudgetResponseDTOFactory.createBudgetResponseDTO();
        when(budgetMapper.toBudget(any(BudgetDTO.class))).thenReturn(budget);
        when(userRepository.findByGoogleId(any(String.class))).thenReturn(Optional.of(UserFactory.createUser()));
        when(budgetRepository.save(any(Budget.class))).thenReturn(budget);
        when(budgetMapper.toBudgetResponseDTO(any(Budget.class))).thenReturn(expectedBudget);
        final BudgetResponseDTO budgetSaved = budgetService.save(budgetDTO, oAuth2AuthenticatedPrincipalImpl);

        verify(budgetMapper).toBudget(budgetDTO);
        verify(userRepository).findByGoogleId("12345");
        verify(budgetRepository).save(budget);
        verify(budgetMapper).toBudgetResponseDTO(budget);
        assertEquals(expectedBudget.id(), budgetSaved.id());
        assertEquals(expectedBudget.clientName(), budgetSaved.clientName());
        assertEquals(expectedBudget.draw(), budgetSaved.draw());
        assertEquals(expectedBudget.centimeter(), budgetSaved.centimeter());
        assertEquals(expectedBudget.pricePerCentimeter(), budgetSaved.pricePerCentimeter());
        assertEquals(expectedBudget.bodyLocal(), budgetSaved.bodyLocal());
        assertEquals(expectedBudget.styles().size(), budgetSaved.styles().size());
        assertEquals(expectedBudget.details().size(), budgetSaved.details().size());
        assertEquals(expectedBudget.description(), budgetSaved.description());
        assertEquals(expectedBudget.studioPercentage(), budgetSaved.studioPercentage());
        assertEquals(expectedBudget.parkingCost(), budgetSaved.parkingCost());
        assertEquals(expectedBudget.materialCost(), budgetSaved.materialCost());
        assertEquals(expectedBudget.creditCardFee(), budgetSaved.creditCardFee());
        assertEquals(expectedBudget.tattooValue(), budgetSaved.tattooValue());
        assertEquals(expectedBudget.studioFee(), budgetSaved.studioFee());
        assertEquals(expectedBudget.netValue(), budgetSaved.netValue());
        assertEquals(expectedBudget.grossValue(), budgetSaved.grossValue());
        assertEquals(expectedBudget.creditCardValue(), budgetSaved.creditCardValue());
        assertEquals(expectedBudget.status(), budgetSaved.status());
        assertEquals(expectedBudget.paymentMethod(), budgetSaved.paymentMethod());
    }

    @Test
    void givenFind_whenBudgetWithInformedIdExist_thenReturnTheExpectedBudget() throws JsonProcessingException {

        final Budget budget = BudgetFactory.createBudget();
        final BudgetResponseDTO expectedBudget = BudgetResponseDTOFactory.createBudgetResponseDTO();

        when(budgetRepository.findById(anyLong())).thenReturn(Optional.of(budget));
        when(budgetMapper.toBudgetResponseDTO(any(Budget.class))).thenReturn(expectedBudget);
        final BudgetResponseDTO budgetFound = budgetService.find(1L);

        verify(budgetRepository).findById(1L);
        verify(budgetMapper).toBudgetResponseDTO(budget);
        assertEquals(expectedBudget.id(), budgetFound.id());
        assertEquals(expectedBudget.clientName(), budgetFound.clientName());
        assertEquals(expectedBudget.draw(), budgetFound.draw());
        assertEquals(expectedBudget.centimeter(), budgetFound.centimeter());
        assertEquals(expectedBudget.pricePerCentimeter(), budgetFound.pricePerCentimeter());
        assertEquals(expectedBudget.bodyLocal(), budgetFound.bodyLocal());
        assertEquals(expectedBudget.styles().size(), budgetFound.styles().size());
        assertEquals(expectedBudget.details().size(), budgetFound.details().size());
        assertEquals(expectedBudget.description(), budgetFound.description());
        assertEquals(expectedBudget.studioPercentage(), budgetFound.studioPercentage());
        assertEquals(expectedBudget.parkingCost(), budgetFound.parkingCost());
        assertEquals(expectedBudget.materialCost(), budgetFound.materialCost());
        assertEquals(expectedBudget.creditCardFee(), budgetFound.creditCardFee());
        assertEquals(expectedBudget.tattooValue(), budgetFound.tattooValue());
        assertEquals(expectedBudget.studioFee(), budgetFound.studioFee());
        assertEquals(expectedBudget.netValue(), budgetFound.netValue());
        assertEquals(expectedBudget.grossValue(), budgetFound.grossValue());
        assertEquals(expectedBudget.creditCardValue(), budgetFound.creditCardValue());
        assertEquals(expectedBudget.status(), budgetFound.status());
        assertEquals(expectedBudget.paymentMethod(), budgetFound.paymentMethod());
    }

    @Test
    void givenFind_whenBudgetWithInformedIdNotExist_thenThrowApplicationException() {

        when(budgetRepository.findById(anyLong())).thenReturn(Optional.empty());
        final ApplicationException applicationException = assertThrowsExactly(ApplicationException.class, () -> budgetService.find(1L));
        verify(budgetRepository).findById(1L);
        assertEquals(404, applicationException.getStatus());
        assertEquals("Orçamento não encontrado.", applicationException.getMessage());
    }

    @Test
    void givenFindAll_whenExistBudgetsForTheLoggedUser_thenReturnListOfBudgets() throws JsonProcessingException {

        final User user = UserFactory.createUser();
        final Budget firstBudget = BudgetFactory.createBudget();
        final Budget secondBudget = BudgetFactory.createBudget();
        when(userRepository.findByGoogleId(any(String.class))).thenReturn(Optional.of(user));
        when(budgetRepository.findAllByUser(any(User.class))).thenReturn(List.of(firstBudget, secondBudget));
        when(budgetMapper.toBudgetResponseDTO(any(Budget.class))).thenReturn(BudgetResponseDTOFactory.createBudgetResponseDTO());
        final List<BudgetResponseDTO> foundBudgets = budgetService.findAll(oAuth2AuthenticatedPrincipalImpl);

        verify(userRepository).findByGoogleId("12345");
        verify(budgetRepository).findAllByUser(user);
        verify(budgetMapper, times(2)).toBudgetResponseDTO(firstBudget);
        assertEquals(2, foundBudgets.size());
    }

    @Test
    void givenUpdate_whenBudgetWithInformedIdNotExist_thenThrowApplicationException() {

        when(budgetRepository.findById(anyLong())).thenReturn(Optional.empty());
        final ApplicationException applicationException = assertThrowsExactly(ApplicationException.class, () -> budgetService.update(1L, UpdateBudgetDTOFactory.createUpdateBudgetDTO()));
        verify(budgetRepository).findById(1L);
        assertEquals(404, applicationException.getStatus());
        assertEquals("Orçamento não encontrado.", applicationException.getMessage());
    }

    @Test
    void givenUpdate_whenInputDataIsNotCorrect_thenThrowApplicationException() throws JsonProcessingException {

        when(budgetRepository.findById(anyLong())).thenReturn(Optional.of(BudgetFactory.createBudget()));
        final ApplicationException applicationException = assertThrowsExactly(ApplicationException.class, () -> budgetService.update(1L, UpdateBudgetDTOFactory.createUpdateBudgetDTOWithoutPaymentMethod()));
        verify(budgetRepository).findById(1L);
        assertEquals(400, applicationException.getStatus());
        assertEquals("Preencha o método de pagamento.", applicationException.getMessage());
    }
}