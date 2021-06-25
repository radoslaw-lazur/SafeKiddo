package com.example.safekiddo.exception.handler;

import com.example.safekiddo.exception.KlazifyClientException;
import com.example.safekiddo.exception.ObjectNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ObjectNotFoundException.class)
    protected ResponseEntity<Object> handleException(ObjectNotFoundException ex) {
        String bodyOfResponse = "NOT FOUND: " + ex.getMessage();
        return new ResponseEntity(bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = KlazifyClientException.class)
    protected ResponseEntity<Object> handleException(KlazifyClientException ex) {
        String bodyOfResponse = "REQUEST: " + ex.getMessage();
        return new ResponseEntity(bodyOfResponse, new HttpHeaders(), HttpStatus.REQUEST_TIMEOUT);
    }
}
