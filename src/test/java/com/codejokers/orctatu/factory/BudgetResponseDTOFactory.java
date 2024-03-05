package com.codejokers.orctatu.factory;

import com.codejokers.orctatu.dto.BudgetResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BudgetResponseDTOFactory {

    public static final String BUDGET_RESPONSE_DTO_JSON = "{\"id\":3,\"clientName\":\"John Doe\",\"draw\":\"Borboleta\",\"centimeter\":40,\"pricePerCentimeter\":20,\"bodyLocal\":\"ARM\",\"styles\":[\"FINELINE\",\"REALISM\"],\"details\":[\"SHADING\",\"LINES\"],\"description\":\"xxxxx\",\"studioPercentage\":20,\"parkingCost\":10,\"materialCost\":190,\"creditCardFee\":20,\"tattooValue\":800,\"studioFee\":160,\"netValue\":640,\"grossValue\":1000,\"creditCardValue\":1020,\"status\":\"BUDGETED\",\"paymentMethod\":null}";

    public static BudgetResponseDTO createBudgetResponseDTO() throws JsonProcessingException {
        return new ObjectMapper().readValue(BUDGET_RESPONSE_DTO_JSON, BudgetResponseDTO.class);
    }
}