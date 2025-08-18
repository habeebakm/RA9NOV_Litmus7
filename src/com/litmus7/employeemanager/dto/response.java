package com.litmus7.employeemanager.dto;

public class response<T> {
    
    private String errorcode;
    private String message;
    private T data;

    public response(String errorcode, String message,T data) {
        
        this.errorcode = errorcode;
        this.message = message;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public String getErrorCode() {
        return errorcode;
    }

    public String getMessage() {
        return message;
    }
}
