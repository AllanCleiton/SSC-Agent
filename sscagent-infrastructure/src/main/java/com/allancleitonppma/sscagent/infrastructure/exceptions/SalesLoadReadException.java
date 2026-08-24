package com.allancleitonppma.sscagent.infrastructure.exceptions;

public class SalesLoadReadException extends RuntimeException {

    public SalesLoadReadException(String message, Throwable cause) {
        super(message, cause);
    }
}