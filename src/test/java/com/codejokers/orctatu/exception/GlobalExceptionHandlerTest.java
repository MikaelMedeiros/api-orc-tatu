package com.codejokers.orctatu.exception;

import com.codejokers.orctatu.dto.ApiErrorDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class GlobalExceptionHandlerTest {

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;
    @Autowired
    private MockMvc mockMvc;
    private static final int INTERNAL_SERVER_ERROR_CODE = 500;

    @Test
    void givenHandleApplicationException_whenApplicationThrowTheSpecificException_thenReturnTheReceivedStatusAndErrorMessage() {

        final String expectedMessage = "test";
        final ResponseEntity<ApiErrorDTO> response = globalExceptionHandler.handleApplicationException(new ApplicationException(INTERNAL_SERVER_ERROR_CODE, expectedMessage));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());

        final ApiErrorDTO apiError = response.getBody();
        assertNotNull(apiError.timestamp());
        assertEquals(INTERNAL_SERVER_ERROR_CODE, apiError.status());
        assertEquals(expectedMessage, apiError.error());
    }

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

    @Test
    void givenHandleUnexpectedExceptions_whenHaveSomeUnexpectedErrorInApplication_thenReturnStatus500() {

        final String expectedMessage = "Erro na aplicação, servidor indisponível, etc.";
        final ResponseEntity<ApiErrorDTO> response = globalExceptionHandler.handleUnexpectedExceptions(new Throwable());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());

        final ApiErrorDTO apiError = response.getBody();
        assertNotNull(apiError.timestamp());
        assertEquals(INTERNAL_SERVER_ERROR_CODE, apiError.status());
        assertEquals(expectedMessage, apiError.error());
    }
}