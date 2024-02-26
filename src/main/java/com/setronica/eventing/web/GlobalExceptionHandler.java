package com.setronica.eventing.web;

import com.setronica.eventing.dto.ErrorResponseDto;
import com.setronica.eventing.exceptions.EntityNotFoundException;
import com.setronica.eventing.exceptions.NotEnoughSeatsAvailableException;
import com.setronica.eventing.exceptions.PaymentFailedException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(
      EntityNotFoundException e, HttpServletRequest request) {
    log.warn("Entity not found:", e);

    ErrorResponseDto errorResponse =
        new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(),
            e.getMessage(),
            request.getRequestURI());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(DataAccessException.class)
  public ResponseEntity<ErrorResponseDto> handleDataAccessException(
      DataAccessException e, HttpServletRequest request) {

    log.error("Data access exception:", e);

    ErrorResponseDto errorResponse =
        new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),
            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
            "An error occurred while accessing the database",
            request.getRequestURI());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }

  @ExceptionHandler(NotEnoughSeatsAvailableException.class)
  public ResponseEntity<ErrorResponseDto> handleNotEnoughSeatsAvailableException(
      NotEnoughSeatsAvailableException e, HttpServletRequest request) {
    log.warn("Not enough seats available:", e);

    ErrorResponseDto errorResponse =
        new ErrorResponseDto(HttpStatus.UNPROCESSABLE_ENTITY.value(),
            HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(),
            e.getMessage(),
            request.getRequestURI());

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
  }

  @ExceptionHandler(PaymentFailedException.class)
  public ResponseEntity<ErrorResponseDto> handlePaymentFailedException(
      PaymentFailedException e, HttpServletRequest request) {
    log.error("Payment failed:", e);

    ErrorResponseDto errorResponse =
        new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            e.getMessage(),
            request.getRequestURI());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }
}
