package com.codejokers.orctatu.factory;

import com.codejokers.orctatu.entity.Budget;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BudgetFactory {

    private static final String BUDGET_JSON = "{\"id\":1,\"googleId\":\"12345\",\"clientName\":\"John Doe\",\"draw\":\"Borboleta\",\"centimeter\":40,\"pricePerCentimeter\":20,\"bodyLocal\":\"LEG\",\"styles\":[\"FINELINE\",\"REALISM\"],\"details\":[\"SHADING\",\"LINES\"],\"description\":\"xxxxx\",\"studioPercentage\":20,\"parkingCost\":10,\"materialCost\":190,\"creditCardFee\":20,\"tattooValue\":800,\"studioFee\":160.0,\"netValue\":640.0,\"grossValue\":1000,\"creditCardValue\":1020,\"status\":\"BUDGETED\",\"paymentMethod\":null}";

    public static Budget createBudget() throws JsonProcessingException {
        return new ObjectMapper().readValue(BUDGET_JSON, Budget.class);
    }
}