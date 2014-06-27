package com.luna.lib.exception;

public class CommandException extends Exception {
    public CommandException() {
        this("Undefined error");
    }

    public CommandException(final String message) {
        super(message);
    }
}
