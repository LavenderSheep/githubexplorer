package com.lavendersheep.githubexplorer;

import org.springframework.http.HttpStatus;

public class ResponseHandler {
    private int status;
    private String message;

    public int getStatus(){
        return status;
    }

    public void setStatus(int newStatus){
        this.status = newStatus;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String newMessage){
        this.message = newMessage;
    }

    public ResponseHandler(HttpStatus httpStatus, String messageString){
        this.status = httpStatus.value();
        this.message = messageString;
    }
}
