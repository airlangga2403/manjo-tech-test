package com.manjo.assement.exception;


import com.manjo.assement.dto.common.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleRequest(BadRequestException ex) {
        return ResponseEntity
                .badRequest()
                .body(
                        ErrorResponse.builder()
                                .responseCode("4004700")
                                .responseMessage(ex.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }


    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(
            UnauthorizedException ex
    ) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ErrorResponse.builder()
                                .responseCode("4014700")
                                .responseMessage(ex.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            TransactionNotFoundException ex
    ) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.builder()
                                .responseCode("4045100")
                                .responseMessage(ex.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception ex
    ) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.builder()
                                .responseCode("5000000")
                                .responseMessage(ex.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingHeader(
            MissingRequestHeaderException ex
    ) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ErrorResponse.builder()
                                .responseCode("4014701")
                                .responseMessage(
                                        "X-SIGNATURE header is required"
                                )
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }
}
