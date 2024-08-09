package com.lavendersheep.githubexplorer;

public class HandleNotFoundError extends RuntimeException{
    public HandleNotFoundError(){
        super();
    }

    public HandleNotFoundError(String message){
        super(message);
    }
}