package com.myproject.exception;

/**
 * The ControllerException class indices the exceptions occur during work in Controller Classes
 */
public class ControllerException extends Exception{

    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ControllerException(Throwable cause) {
        super(cause);
    }
}
