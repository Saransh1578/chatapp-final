package com.saransh.chatappbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.saransh.chatappbackend.dto.UserResponseDTO;


@ControllerAdvice
public class GlobalExceptionHandler {

  
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<UserResponseDTO> handleInternalServerErrorException(InternalServerErrorException e) {
        return new ResponseEntity<>(
                UserResponseDTO.builder()
                        .statusCode(500)
                        .status("Failed")
                        .reason("I - " + e.getMessage())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

   
    @ExceptionHandler(Exception.class)
    public ResponseEntity<UserResponseDTO> handleGeneralException(Exception e) {
        return new ResponseEntity<>(
                UserResponseDTO.builder()
                        .statusCode(500)
                        .status("Failed")
                        .reason(e.getMessage())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}