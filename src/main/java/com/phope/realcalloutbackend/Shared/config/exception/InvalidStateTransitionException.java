package com.phope.realcalloutbackend.Shared.config.exception;

public class InvalidStateTransitionException extends CalloutException{
    public InvalidStateTransitionException(String from, String to) {
        super("Invalid status transition: " + from + " → " + to);
    }

}
