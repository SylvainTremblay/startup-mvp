package com.csofdv.bmvpf.exception;

public class GenerationServiceException extends Exception {

    public GenerationServiceException(String message) {
        super(message);
    }

    public GenerationServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenerationServiceException(Throwable cause) {
        super(cause);
    }
}
