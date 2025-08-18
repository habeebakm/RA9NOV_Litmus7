package com.litmus7.employeemanager.constants;

public class exception extends Exception {
    
    private String code; 

    public exception(String message) {
        super(message);
    }

    public exception(String code, String message) {
        super(message);     
        this.code = code;  
    }

    public String getCode() {
        return code;
    }
}
