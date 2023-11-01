package com.bksproject.bksproject.exception.System;


import org.springframework.web.bind.annotation.RestControllerAdvice;


public class EmailExistException extends Exception {
    public EmailExistException(String message) {
        super(message);
    }

}
