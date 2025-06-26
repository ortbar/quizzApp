package com.quizzapp.exceptions;


import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // para logica de negocio
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return new ResponseEntity<>(new ApiErrorResponse(ex.getMessage(), 400, LocalDateTime.now().toString()), HttpStatus.BAD_REQUEST);
    }

    // Manejador para UserNotFoundException - logica de negocio
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Manejador para UserAlreadyExistsException
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<String> handleQuestionNotFoundException(QuestionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<String> handlerResourceNotFoundException(ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        ApiErrorResponse error = new ApiErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now().toString()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

        String errorMessage = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(" | "));

        return new ResponseEntity<>(new ApiErrorResponse(errorMessage, 400, LocalDateTime.now().toString()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAllExceptions(Exception ex) {
        ApiErrorResponse error = new ApiErrorResponse(
                "Error interno del servidor",
                500,
                LocalDateTime.now().toString()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleBadCredentialsException(Exception ex) {
        ApiErrorResponse error = new ApiErrorResponse(
                "Contraseña incorrecta",
                401,
                LocalDateTime.now().toString()
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameInUseException.class)
    public ResponseEntity<ApiErrorResponse> handleUsernameAlreadyExists(UsernameInUseException ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                "Username en uso, elija otro",
                400,
                LocalDateTime.now().toString()
        );

        return new ResponseEntity<>(new ApiErrorResponse(ex.getMessage(), 400,LocalDateTime.now().toString()),HttpStatus.BAD_REQUEST);
    }

        @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ApiErrorResponse> handleIncorrectPassword(IncorrectPasswordException ex) {
        ApiErrorResponse error = new ApiErrorResponse(
            ex.getMessage(),
            HttpStatus.BAD_REQUEST.value(),
            Instant.now().toString()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                "Email en uso, elija otro",
                400,
                LocalDateTime.now().toString()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }






}
