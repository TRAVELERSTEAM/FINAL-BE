package com.travelers.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.travelers.exception.ErrorCode.DUPLICATE_RESOURCE;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { ConstraintViolationException.class, DataIntegrityViolationException.class})
    protected ResponseEntity<ErrorResponseDto> handleDataException() {
        log.error("handleDataException throw Exception : {}", DUPLICATE_RESOURCE);
        return ErrorResponseDto.toResponseEntity(DUPLICATE_RESOURCE);
    }

    @ExceptionHandler(value = { TravelersException.class })
    protected ResponseEntity<ErrorResponseDto> handleCustomException(TravelersException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ErrorResponseDto.toResponseEntity(e.getErrorCode());
    }
}
