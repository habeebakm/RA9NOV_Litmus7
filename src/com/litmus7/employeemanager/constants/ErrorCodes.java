package com.litmus7.employeemanager.constants;

public interface ErrorCodes {

    // General errors
    String UNKNOWN_ERROR = "ERR999";
    String SUCCESS = "";
    String INVALID_INPUT = "ERR005";

    // Employee-specific errors
    String INVALID_EMPLOYEE_ID = "ERR001";
    String EMPLOYEE_NOT_FOUND = "ERR002";
    String DUPLICATE_EMPLOYEE = "ERR008";

    // Database/SQL errors
    String DATABASE_ERROR  = "ERR003";
    String SQL_QUERY_FAILED = "ERR004";

    // File-related errors
    String FILE_READ_ERROR = "ERR006";
    String FILE_WRITE_ERROR = "ERR007";

    // Validation-specific errors
    String INVALID_ID = "VAL001";
    String INVALID_NAME = "VAL002";
    String INVALID_MOBILE = "VAL004";
    String INVALID_EMAIL = "VAL005";
    String INVALID_DATE = "VAL006";
    String INVALID_STATUS = "VAL007";
    
    
    
    public static final String NO_ROWS_INSERTED = "ERR_NO_ROWS_INSERTED"; 
    public static final String EMPLOYEE_ALREADY_EXISTS = "ERR_EMPLOYEE_ALREADY_EXISTS"; 

 
    // UPDATE
    public static final String NO_ROWS_UPDATED = "ERR_NO_ROWS_UPDATED";
    public static final String UPDATE_FAILED = "ERR_UPDATE_FAILED"; 

    // DELETE
    public static final String NO_ROWS_DELETED = "ERR_NO_ROWS_DELETED";
    public static final String DELETE_FAILED = "ERR_DELETE_FAILED"; 


}
