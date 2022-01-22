package com.example.folksdevurlshortener.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(x->{
                    String fieldName = ((FieldError)x).getField();
                    String errorMessage = x.getDefaultMessage();
                    errors.put(fieldName,errorMessage);
                });

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ShortUrlNotFoundException.class)
    public ResponseEntity<?> shortUrlNotFoundException(ShortUrlNotFoundException e){
        Map<String , String > errors = new HashMap<>();
        errors.put("error",e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(CodeAlreadyExists.class)
    public ResponseEntity<?> codeAlreadyExists(CodeAlreadyExists e){
        Map<String , String > errors = new HashMap<>();
        errors.put("error",e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }



}
