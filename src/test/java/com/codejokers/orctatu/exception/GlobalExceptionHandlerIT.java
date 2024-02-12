package com.codejokers.orctatu.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class GlobalExceptionHandlerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenHandleMethodArgumentNotValidException_whenInputDataIsWrong_thenReturnStatus400AndTheErrorList() throws Exception {

        mockMvc.perform(post("/budgets")
               .contentType(MediaType.APPLICATION_JSON_VALUE)
               .content("{}"))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.status").value(400))
               .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    void givenHandleNoResourceFoundException_whenPathDoesNotExist_thenReturnStatus404AndErrorMessage() throws Exception {

        mockMvc.perform(post("/wrong-url"))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.status").value(404))
               .andExpect(jsonPath("$.error").value("Esse path não existe."));
    }
}