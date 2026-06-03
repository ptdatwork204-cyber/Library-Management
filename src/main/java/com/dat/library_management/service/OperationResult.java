package com.dat.library_management.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperationResult {

    private boolean success;
    private String message;
    private Object data;

    public static OperationResult ok(String message) {
        return new OperationResult(true, message, null);
    }

    public static OperationResult ok(String message, Object data) {
        return new OperationResult(true, message, data);
    }

    public static OperationResult error(String message) {
        return new OperationResult(false, message, null);
    }
}