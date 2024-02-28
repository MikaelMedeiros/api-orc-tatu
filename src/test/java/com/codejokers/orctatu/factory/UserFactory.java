package com.codejokers.orctatu.factory;

import com.codejokers.orctatu.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class UserFactory {

    private static final String CALCULATION_SETTINGS_JSON = "{\"id\":1,\"googleId\":\"12345\",\"name\":\"John Doe\",\"picture\":\"10000xxxxx\",\"email\":\"xxx@gmail.com\",\"emailVerified\":true,\"locale\":\"pt-BR\",\"authorities\":\"USER\",\"createdAt\":\"2024-02-28T09:49:22.836435200\"}";

    public static User createUser() throws JsonProcessingException {
        return new ObjectMapper().registerModule(new JavaTimeModule()).readValue(CALCULATION_SETTINGS_JSON, User.class);
    }
}