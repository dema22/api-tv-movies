package com.example.spring.controllers;

import com.example.spring.dto.ErrorResponseDTO;
import com.example.spring.exception.ForbiddenActionExcepction;
import com.example.spring.exception.ResourceAlreadyExistsException;
import com.example.spring.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestControllerAdvice
public class ControllerAdvice  extends ResponseEntityExceptionHandler {

    // 400: Bad request -> The 400 Bad Request Error is an HTTP response status code that indicates that the server was unable to process the request sent by the client due to invalid syntax.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    protected ErrorResponseDTO handleDuplicateEntity(HttpServletRequest request, ResourceAlreadyExistsException ex) {
        return new ErrorResponseDTO(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
    }

    // 204: No Content -> no content for the target resource.
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    protected ErrorResponseDTO handleEntityNotFound(HttpServletRequest request, ResourceNotFoundException ex) {
        return new ErrorResponseDTO(new Date(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
    }

    // 403: Forbidden -> The request contained valid data and was understood by the server, but the server is refusing action
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenActionExcepction.class)
    protected ErrorResponseDTO handleForbiddenAction(HttpServletRequest request, ForbiddenActionExcepction ex) {
        return new ErrorResponseDTO(new Date(), HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
    }


}
