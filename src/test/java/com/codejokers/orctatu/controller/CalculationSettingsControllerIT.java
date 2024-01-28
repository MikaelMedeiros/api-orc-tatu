package com.codejokers.orctatu.controller;

import com.codejokers.orctatu.entity.CalculationSettings;
import com.codejokers.orctatu.factory.CalculationSettingsDTOFactory;
import com.codejokers.orctatu.factory.CalculationSettingsFactory;
import com.codejokers.orctatu.repository.CalculationSettingsRepository;
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
class CalculationSettingsControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CalculationSettingsRepository calculationSettingsRepository;
    private static final String PATH = "/calculation-settings";

    @BeforeEach
    void setUp() {
        calculationSettingsRepository.deleteAll();
    }

    @Test
    void givenSave_whenRequestDataIsCorrect_thenSaveInTheDatabaseAndReturnSavedCalculationSettingsAndStatus201() throws Exception {

        final CalculationSettings expectedCalculationSettings = CalculationSettingsFactory.createCalculationSettings();

        assertEquals(0, calculationSettingsRepository.count());

        mockMvc.perform(post(PATH)
               .accept(MediaType.APPLICATION_JSON)
               .contentType(MediaType.APPLICATION_JSON)
               .content(CalculationSettingsDTOFactory.CALCULATION_SETTINGS_DTO_JSON))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(expectedCalculationSettings.getId()))
               .andExpect(jsonPath("$.pricePerCentimeter").value(expectedCalculationSettings.getPricePerCentimeter()))
               .andExpect(jsonPath("$.studioPercentage").value(expectedCalculationSettings.getStudioPercentage()))
               .andExpect(jsonPath("$.parkingCost").value(expectedCalculationSettings.getParkingCost()))
               .andExpect(jsonPath("$.materialCost").value(expectedCalculationSettings.getMaterialCost()))
               .andExpect(jsonPath("$.creditCardFee").value(expectedCalculationSettings.getCreditCardFee()));

        assertEquals(1, calculationSettingsRepository.count());
    }

    @Test
    void givenSave_whenRequestDataIsNotCorrect_thenReturnErrorResponseAndStatus400() throws Exception {

        assertEquals(0, calculationSettingsRepository.count());

        mockMvc.perform(post(PATH)
               .accept(MediaType.APPLICATION_JSON)
               .contentType(MediaType.APPLICATION_JSON)
               .content("{}"))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.timestamp").exists())
               .andExpect(jsonPath("$.status").value(400))
               .andExpect(jsonPath("$.errors").isArray());

        assertEquals(0, calculationSettingsRepository.count());
    }
}