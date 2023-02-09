package com.muhammadusman92.userservice.exception;

public class AlreadyExistExeption extends RuntimeException{
    private String fieldName;
    private String fieldValue;

    public AlreadyExistExeption(String fieldName, String fieldValue) {
        super(String.format("%s : %s is already exist in database",fieldName,fieldValue));
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
