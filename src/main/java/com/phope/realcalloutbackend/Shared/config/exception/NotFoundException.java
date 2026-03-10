package com.phope.realcalloutbackend.Shared.config.exception;

public class NotFoundException extends CalloutException{
    public  NotFoundException(String resource, Object Id){
        super(resource + " not found: " + Id );
    }
}
