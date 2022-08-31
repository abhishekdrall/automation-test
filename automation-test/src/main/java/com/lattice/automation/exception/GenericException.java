package com.lattice.automation.exception;

import lombok.Data;

@Data
public class GenericException extends Exception{
    private int statusCode;
    private String message;

    public GenericException(int statusCode,String message){
        super(message);
        this.statusCode=statusCode;
        this.message=message;
    }
}
