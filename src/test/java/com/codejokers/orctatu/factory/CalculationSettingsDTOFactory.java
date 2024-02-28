package com.codejokers.orctatu.factory;

import com.codejokers.orctatu.dto.CalculationSettingsDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CalculationSettingsDTOFactory {

    public static final String CALCULATION_SETTINGS_DTO_JSON = "{\"pricePerCentimeter\":20,\"studioPercentage\":20,\"parkingCost\":10000,\"materialCost\":190,\"creditCardFee\":20}";

    public static CalculationSettingsDTO createCalculationSettingsDTO() throws JsonProcessingException {
        return new ObjectMapper().readValue(CALCULATION_SETTINGS_DTO_JSON, CalculationSettingsDTO.class);
    }
}