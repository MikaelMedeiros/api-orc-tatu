package com.codejokers.orctatu.factory;

import com.codejokers.orctatu.entity.CalculationSettings;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CalculationSettingsFactory {

    private static final String CALCULATION_SETTINGS_JSON = "{\"id\":\"114391191300801853500\",\"pricePerCentimeter\":20,\"studioPercentage\":20,\"parkingCost\":10000,\"materialCost\":190,\"creditCardFee\":20}";

    public static CalculationSettings createCalculationSettings() throws JsonProcessingException {
        return new ObjectMapper().readValue(CALCULATION_SETTINGS_JSON, CalculationSettings.class);
    }
}