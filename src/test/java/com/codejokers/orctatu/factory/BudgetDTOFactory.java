package com.codejokers.orctatu.factory;

import com.codejokers.orctatu.dto.BudgetDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BudgetDTOFactory {

    public static final String BUDGET_DTO_JSON = "{\"clientName\":\"John Doe\",\"draw\":\"Borboleta\",\"centimeter\":40,\"pricePerCentimeter\":20,\"bodyLocals\":[\"LEG\",\"ARM\"],\"styles\":[\"FINELINE\",\"REALISM\"],\"details\":[\"SHADING\",\"LINES\"],\"description\":\"xxxxx\",\"studioPercentage\":20,\"parkingCost\":10,\"materialCost\":190,\"creditCardFee\":20}";

    public static BudgetDTO createBudgetDTO() throws JsonProcessingException {
        return new ObjectMapper().readValue(BUDGET_DTO_JSON, BudgetDTO.class);
    }
}