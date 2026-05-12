package com.example.phungloc.exception;

import com.example.phungloc.dto.response.ErrorDetail;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetail> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String msg = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

        ErrorDetail error = ErrorDetail.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .errorCode(ErrorCode.BAD_REQUEST)
                .message(msg)
                .path(request.getRequestURI())
                .build();

        log.warn("Lỗi nhập liệu: {} tại {}", msg, request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorDetail> handleParamValidation(HandlerMethodValidationException ex, HttpServletRequest request) {
        String msg = "Tham số không hợp lệ";

        if (!ex.getAllErrors().isEmpty()) {
            msg = ex.getAllErrors().get(0).getDefaultMessage();
        }

        ErrorDetail error = ErrorDetail.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .errorCode(ErrorCode.BAD_REQUEST)
                .message(msg)
                .path(request.getRequestURI())
                .build();

        log.warn("Lỗi tham số URL: {} tại {}", msg, request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorDetail> handleAppException(AppException ex, HttpServletRequest request) {
        ErrorDetail error = ErrorDetail.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .errorCode(ex.getErrorCode())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}