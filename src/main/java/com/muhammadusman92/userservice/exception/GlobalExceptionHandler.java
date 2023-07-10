package com.muhammadusman92.userservice.exception;

import com.muhammadusman92.userservice.payloads.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Response> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        return new ResponseEntity<>(Response.builder()
                .timeStamp(now())
                .status(NOT_FOUND)
                .statusCode(NOT_FOUND.value())
                .message(ex.getMessage())
                .build(), NOT_FOUND);
    }
    @ExceptionHandler(AlreadyExistExeption.class)
    public ResponseEntity<Response> alreadyExistHandler(AlreadyExistExeption ex){
        return new ResponseEntity<>(Response.builder()
                .timeStamp(now())
                .status(BAD_REQUEST)
                .statusCode(BAD_REQUEST.value())
                .message(ex.getMessage())
                .build(), OK);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response>  methodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String,String> map=new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(objectError -> {
            String fieldName = ((FieldError) objectError).getField();
            String fieldError = objectError.getDefaultMessage();
            map.put(fieldName,fieldError);
        });
        return new ResponseEntity<>(Response.builder()
                .timeStamp(now())
                .status(BAD_REQUEST)
                .statusCode(BAD_REQUEST.value())
                .data(map)
                .build(),OK);
    }
}
