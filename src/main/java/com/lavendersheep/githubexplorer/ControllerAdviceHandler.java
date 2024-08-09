package com.lavendersheep.githubexplorer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviceHandler {

    @ExceptionHandler(HandleNotFoundError.class)
    public ResponseEntity<ResponseHandler> handleNotFoundErrorResponse(Exception e){
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = "User not found!";

        return new ResponseEntity<ResponseHandler>(new ResponseHandler(status, message), status);
    }
    
}
