package com.example.demo.exception;

public enum ErrorCode {
    USER_EXISTED(1234, "User Existed . "),
    UNAUTHENTICATED(1005, "Not login ")
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code ;
    private String message ;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
