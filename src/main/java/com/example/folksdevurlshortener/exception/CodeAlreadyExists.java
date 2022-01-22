package com.example.folksdevurlshortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CodeAlreadyExists extends RuntimeException{
    public CodeAlreadyExists(String message){
        super(message);
    }
}
