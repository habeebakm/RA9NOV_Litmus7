package com.litmus7.employeemanager.dto;

public class response<T, U, V> {
    private T data;
    private U applicationStatus;
    private V message;

    public response(T data, U applicationStatus, V message) {
        this.data = data;
        this.applicationStatus = applicationStatus;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public U getApplicationStatus() {
        return applicationStatus;
    }

    public V getMessage() {
        return message;
    }
}
