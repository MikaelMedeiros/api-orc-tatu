package com.codejokers.orctatu.factory;

import com.codejokers.orctatu.dto.BudgetDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BudgetDTOFactory {

    public static final String BUDGETDTO_JSON = "{\"clientName\":\"John Doe\",\"draw\":\"Borboleta\",\"centimeter\":10,\"bodyLocals\":[\"LEG\",\"ARM\"],\"styles\":[\"FINELINE\",\"REALISM\"],\"details\":[\"SHADING\",\"LINES\"],\"description\":\"Jhon Doe, sua tatto de borboleta custou 100 reais\",\"studioPercentage\":30,\"parkingPrice\":10,\"creditFee\":20,\"materialPrice\":50,\"pricePerCentimeter\":22}";

    public static BudgetDTO createBudgetDTO() throws JsonProcessingException {
        return new ObjectMapper().readValue(BUDGETDTO_JSON, BudgetDTO.class);
    }
}