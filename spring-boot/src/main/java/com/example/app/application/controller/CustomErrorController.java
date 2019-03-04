package com.example.app.application.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RestControllerAdvice
public class CustomErrorController extends ResponseEntityExceptionHandler implements ErrorController {

    final private String ERROR_RESPONSE_KEY = "status";
    final private Map<String, Integer> response = new HashMap<>();

    @RequestMapping("/error")
    public Map<String, Integer> error(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            response.put(ERROR_RESPONSE_KEY, Integer.parseInt(status.toString()));
        }
        return response;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(HttpClientErrorException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = ex.getStatusCode();
        response.put(ERROR_RESPONSE_KEY, status.value());

        return this.handleExceptionInternal(ex, response, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(body, headers, status);
    }
}
