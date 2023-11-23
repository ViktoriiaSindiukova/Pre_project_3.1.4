package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.kata.spring.boot_security.demo.utill.InvalidDataException;
import ru.kata.spring.boot_security.demo.utill.Response;
import ru.kata.spring.boot_security.demo.utill.UserNotFoundException;

@ControllerAdvice
public class ErrorHandlingController {

    @ExceptionHandler
    public ResponseEntity<Response> userNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(new Response(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Response> exceptionHandler(InvalidDataException ex) {
        return new ResponseEntity<>(new Response(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
