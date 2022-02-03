package com.lulo.lulobank.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler 
  extends ResponseEntityExceptionHandler {

    
    @ExceptionHandler(AccountException.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException
                        (AccountException ex, WebRequest request) 
    {
        List<String> details = new ArrayList<String>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("errror", details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
 
}
