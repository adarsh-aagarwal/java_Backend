package com.exaple.quiet_Q.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(UserExcepition.class)
    public ResponseEntity<ErrorDetails> userExceptionHandler(UserExcepition e, WebRequest req){
        ErrorDetails er=new ErrorDetails(e.getMessage(),req.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(er, HttpStatus.OK);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> otherExceptionHandler(Exception e, WebRequest req){
        ErrorDetails er=new ErrorDetails(e.getMessage(),req.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(er, HttpStatus.OK);
    }

}
