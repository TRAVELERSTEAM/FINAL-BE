package com.travelers.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

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
    protected ResponseEntity<ErrorResponseDto> handleCustomException(final TravelersException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ErrorResponseDto.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(value = { IllegalStateException.class } )
    public ResponseEntity<String> handlerException() {
        return ResponseEntity
                .badRequest()
                .body("잘못된 요청 값 입니다.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validHandling(final BindingResult br) {

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(br));
    }

    @Getter
    final static class ErrorResponse  {
        private final Map<String, Object> rejectPair = new HashMap<>();

        ErrorResponse(final BindingResult bindingResult) {
            bindingResult.getFieldErrors()
                    .forEach(e -> rejectPair.put(e.getField(), e.getRejectedValue()));
        }
    }
}
