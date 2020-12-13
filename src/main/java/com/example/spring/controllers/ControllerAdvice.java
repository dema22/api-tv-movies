package com.example.spring.controllers;

import com.example.spring.dto.ErrorResponseDTO;
import com.example.spring.exception.BusinessLogicValidationFailure;
import com.example.spring.exception.ForbiddenActionExcepction;
import com.example.spring.exception.ResourceAlreadyExistsException;
import com.example.spring.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerAdvice  extends ResponseEntityExceptionHandler {


    // The HyperText Transfer Protocol (HTTP) 422 Unprocessable Entity response status code indicates that the server understands the content type of the request entity, and the syntax of the request entity is correct, but it was unable to process the contained instructions.
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(BusinessLogicValidationFailure.class)
    protected ErrorResponseDTO handleBussinesLogicValidationFailure(HttpServletRequest request, BusinessLogicValidationFailure ex) {
        return new ErrorResponseDTO(LocalDateTime.now(), HttpStatus.UNPROCESSABLE_ENTITY.value(), HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
    }

    // 400: Bad request -> The 400 Bad Request Error is an HTTP response status code that indicates that the server was unable to process the request sent by the client due to invalid syntax.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    protected ErrorResponseDTO handleDuplicateEntity(HttpServletRequest request, ResourceAlreadyExistsException ex) {
        return new ErrorResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
    }

    // 404: Not found -> This means the file or page that the browser is requesting wasn't found by the server.
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    protected ErrorResponseDTO handleEntityNotFound(HttpServletRequest request, ResourceNotFoundException ex) {
        return new ErrorResponseDTO(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
    }

    // 403: Forbidden -> The request contained valid data and was understood by the server, but the server is refusing action
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenActionExcepction.class)
    protected ErrorResponseDTO handleForbiddenAction(HttpServletRequest request, ForbiddenActionExcepction ex) {
        return new ErrorResponseDTO(LocalDateTime.now(), HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
       String uri = ((ServletWebRequest)request).getRequest().getRequestURI();
       List<String> errorMessages = new ArrayList<>();
       String joinedErrorMessages;

       for(ObjectError error : ex.getBindingResult().getAllErrors()){
            errorMessages.add(error.getDefaultMessage());
       }

       joinedErrorMessages = String.join(", ", errorMessages);
       return new ResponseEntity<>(new ErrorResponseDTO(LocalDateTime.now(), status.value(), status.getReasonPhrase(), joinedErrorMessages, uri), headers , status);
    }
}
