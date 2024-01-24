package com.codejokers.orctatu.controller;

import com.codejokers.orctatu.entity.Budget;
import com.codejokers.orctatu.factory.BudgetDTOFactory;
import com.codejokers.orctatu.factory.BudgetFactory;
import com.codejokers.orctatu.repository.BudgetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BudgetControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BudgetRepository budgetRepository;

    @BeforeEach
    void setUp() {
        budgetRepository.deleteAll();
    }

    @Test
    void givenSave_whenRequestDataIsCorrect_thenSaveInTheDatabaseAndReturnSavedBudgetAndStatus201() throws Exception {

        final Budget expectedBudget = BudgetFactory.createBudget();

        assertEquals(0, budgetRepository.count());

        mockMvc.perform(post("/budgets")
               .accept(MediaType.APPLICATION_JSON)
               .contentType(MediaType.APPLICATION_JSON)
               .content(BudgetDTOFactory.BUDGETDTO_JSON))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(expectedBudget.getId()))
               .andExpect(jsonPath("$.googleId").value(expectedBudget.getGoogleId()))
               .andExpect(jsonPath("$.clientName").value(expectedBudget.getClientName()))
               .andExpect(jsonPath("$.draw").value(expectedBudget.getDraw()))
               .andExpect(jsonPath("$.centimeter").value(expectedBudget.getCentimeter()))
               .andExpect(jsonPath("$.pricePerCentimeter").value(expectedBudget.getPricePerCentimeter()))
               .andExpect(jsonPath("$.bodyLocals").isArray())
               .andExpect(jsonPath("$.styles[0]").value(expectedBudget.getStyles().get(0).toString()))
               .andExpect(jsonPath("$.details").isArray())
               .andExpect(jsonPath("$.description").value(expectedBudget.getDescription()))
               .andExpect(jsonPath("$.studioPercentage").value(expectedBudget.getStudioPercentage()))
               .andExpect(jsonPath("$.parkingCost").value(expectedBudget.getParkingCost()))
               .andExpect(jsonPath("$.materialCost").value(expectedBudget.getMaterialCost()))
               .andExpect(jsonPath("$.creditCardFee").value(expectedBudget.getCreditCardFee()))
               .andExpect(jsonPath("$.tattooValue").value(expectedBudget.getTattooValue()))
               .andExpect(jsonPath("$.studioFee").value(expectedBudget.getStudioFee()))
               .andExpect(jsonPath("$.netValue").value(expectedBudget.getNetValue()))
               .andExpect(jsonPath("$.grossValue").value(expectedBudget.getGrossValue()))
               .andExpect(jsonPath("$.creditCardValue").value(expectedBudget.getCreditCardValue()))
               .andExpect(jsonPath("$.status").value(expectedBudget.getStatus().toString()))
               .andExpect(jsonPath("$.paymentMethod").value(expectedBudget.getPaymentMethod()));

        assertEquals(1, budgetRepository.count());
    }

    @Test
    void givenSave_whenRequestDataIsNotCorrect_thenReturnErrorResponseAndStatus400() throws Exception {

        assertEquals(0, budgetRepository.count());

        mockMvc.perform(post("/budgets")
               .accept(MediaType.APPLICATION_JSON)
               .contentType(MediaType.APPLICATION_JSON)
               .content("{}"))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.timestamp").exists())
               .andExpect(jsonPath("$.status").value(400))
               .andExpect(jsonPath("$.errors").isArray());

        assertEquals(0, budgetRepository.count());
    }
}