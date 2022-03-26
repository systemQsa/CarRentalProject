package com.myproject.exception;

/**
 * The CommandException class indicates the exceptions occurs during the work in Command Classes
 */
public class CommandException extends Exception{

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandException(Throwable cause) {
        super(cause);
    }
}
