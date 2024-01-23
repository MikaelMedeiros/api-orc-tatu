package com.codejokers.orctatu.factory;

import com.codejokers.orctatu.entity.Budget;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BudgetFactory {

    private static final String BUDGET_JSON = "{\"id\":1,\"googleId\":null,\"clientName\":\"John Doe\",\"draw\":\"Borboleta\",\"centimeter\":10,\"bodyLocals\":[\"LEG\",\"ARM\"],\"styles\":[\"FINELINE\",\"REALISM\"],\"details\":[\"SHADING\",\"LINES\"],\"description\":\"Jhon Doe, sua tatto de borboleta custou 100 reais\",\"studioPercentage\":30,\"parkingPrice\":10,\"creditFee\":20,\"materialPrice\":50,\"pricePerCentimeter\":22,\"taxPercentage\":null,\"tattooValue\":null,\"netValue\":null,\"status\":\"BUDGETED\"}";

    public static Budget createBudget() throws JsonProcessingException {
        return new ObjectMapper().readValue(BUDGET_JSON, Budget.class);
    }
}