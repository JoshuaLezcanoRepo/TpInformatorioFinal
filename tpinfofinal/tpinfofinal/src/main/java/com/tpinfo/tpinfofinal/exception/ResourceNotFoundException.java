package com.tpinfo.tpinfofinal.exception;

import org.springframework.web.util.NestedServletException;

public class ResourceNotFoundException extends NestedServletException {
    public ResourceNotFoundException(String message){
        super(message);
    }
}