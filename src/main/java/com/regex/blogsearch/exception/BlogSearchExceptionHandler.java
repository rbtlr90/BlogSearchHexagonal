package com.regex.blogsearch.exception;

import com.regex.blogsearch.dto.BlogSearchErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import static com.regex.blogsearch.exception.BlogSearchErrorCode.INTERNAL_SERVER_ERROR;
import static com.regex.blogsearch.exception.BlogSearchErrorCode.INVALID_REQUEST;

@RestControllerAdvice
@Slf4j
public class BlogSearchExceptionHandler {
    @ExceptionHandler()
    public BlogSearchErrorResponse handleException(
            BlogSearchException e, HttpServletRequest request
    ) {
        log.error("errorCode: {}, url: {}, message: {}",
                e.getBlogSearchErrorCode(), request.getRequestURI(), e.getDetail());
        return BlogSearchErrorResponse.builder()
                .errorCode(e.getBlogSearchErrorCode())
                .errorMessage(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = {
            HttpRequestMethodNotSupportedException.class,
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BlogSearchErrorResponse handleBadRequest(
            Exception e, HttpServletRequest request
    ) {
        log.error("url: {}, message: {}",
                request.getRequestURI(), e.getMessage());
        return BlogSearchErrorResponse.builder()
                .errorCode(INVALID_REQUEST)
                .errorMessage(e.getMessage().split(": ")[1])
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BlogSearchErrorResponse handleGeneralException(
            Exception e, HttpServletRequest request
    ) {
        log.error("url: {}, message: {}",
                request.getRequestURI(), e.getMessage());
        return BlogSearchErrorResponse.builder()
                .errorCode(INTERNAL_SERVER_ERROR)
                .errorMessage(INTERNAL_SERVER_ERROR.getMessage())
                .build();
    }
}
