package com.example.stationManagement.model;

public class CommonResult {
    private String message;

    public CommonResult() {
    }

    public CommonResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
