package com.example.app.application.controller;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RestControllerAdvice
public class CustomErrorController extends ResponseEntityExceptionHandler implements ErrorController {

    final private String ERROR_RESPONSE_KEY = "status";
    final private Map<String, String> response = new HashMap<>();

    @RequestMapping("/error")
    public Map<String, String> error404(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            response.put(ERROR_RESPONSE_KEY, String.valueOf(status.toString()));
        }
        return response;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        StringBuilder sb = new StringBuilder();
        fieldErrors.forEach(fieldError ->
                sb.append(fieldError.getField())
                        .append(": ")
                        .append(fieldError.getDefaultMessage())
                        .append("; ")
        );

        return this.handleExceptionInternal(ex, sb.toString(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handle404(NotFoundException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.NOT_FOUND;
        response.put(ERROR_RESPONSE_KEY, "404");

        return handleExceptionInternal(ex, response, headers, status, request);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handle500(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        response.put(ERROR_RESPONSE_KEY, "500");

        return this.handleExceptionInternal(ex, response, headers, status, request);
    }

}
