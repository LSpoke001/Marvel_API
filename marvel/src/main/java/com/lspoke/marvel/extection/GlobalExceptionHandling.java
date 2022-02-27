package com.lspoke.marvel.extection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandling {
    @ExceptionHandler
    public ResponseEntity<InfoException> handleException(Exception exception){
        InfoException data = new InfoException();
        data.setError(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<InfoException> handleException(FileNotFoundException exception){
        InfoException data = new InfoException();
        data.setError(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<InfoException> handleException(FileStorageException exception){
        InfoException data = new InfoException();
        data.setError(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
