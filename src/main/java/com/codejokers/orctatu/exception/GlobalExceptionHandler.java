package com.codejokers.orctatu.exception;

import com.codejokers.orctatu.dto.ApiErrorDTO;
import com.codejokers.orctatu.dto.ApiErrorListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    ResponseEntity<ApiErrorDTO> handleApplicationException(final ApplicationException exception) {
        return ResponseEntity.status(exception.getStatus()).body(setApiErrorDTO(exception.getStatus(), exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiErrorListDTO> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(setApiErrorListDTO(toStringArray(exception.getBindingResult().getAllErrors())));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    ResponseEntity<ApiErrorDTO> handleNoResourceFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(setApiErrorDTO(404, "Esse path não existe."));
    }

    @ExceptionHandler(Throwable.class)
    ResponseEntity<ApiErrorDTO> handleUnexpectedExceptions(final Throwable exception) {
        log.error("{0}", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(setApiErrorDTO(500, "Erro na aplicação, servidor indisponível, etc."));
    }

    private ApiErrorDTO setApiErrorDTO(final Integer status, final String error) {
        return new ApiErrorDTO(LocalDateTime.now(), status, error);
    }

    private ApiErrorListDTO setApiErrorListDTO(final List<String> errors) {
        return new ApiErrorListDTO(LocalDateTime.now(), 400, errors);
    }

    private List<String> toStringArray(final List<ObjectError> objectErrors) {
        return objectErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
    }
}