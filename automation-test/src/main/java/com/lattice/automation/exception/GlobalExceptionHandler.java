package com.lattice.automation.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.sql.SQLException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<?> handlerGenericException(GenericException genericException){
        log.error(genericException.getMessage());
        return ResponseEntity.status(genericException.getStatusCode()).body(genericException.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handlerIOException(IOException ioException){
        log.error(ioException.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ioException.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<?> handlerSQLException(SQLException sqlException){
        log.error(sqlException.getMessage());
        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(sqlException.getMessage());
    }
}
