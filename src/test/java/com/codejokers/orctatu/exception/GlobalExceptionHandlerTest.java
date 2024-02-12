package com.codejokers.orctatu.exception;

import com.codejokers.orctatu.dto.ApiErrorDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;
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