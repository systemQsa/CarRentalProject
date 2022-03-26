package com.myproject.exception;

/**
 * The ValidationException class indicates exceptions occur during validation process
 */
public class ValidationException extends Exception{
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }
}
