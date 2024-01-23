package com.codejokers.orctatu.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private int status;

    public ApplicationException(final int status, final String message) {
        this(message, null);
        this.status = status;
    }

    private ApplicationException(final String message, final Throwable cause) {
        super(message, cause, true, false);
    }
}