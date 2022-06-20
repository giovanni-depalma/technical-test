package com.example.exercise1.app.request;

public record Request(long timestamp, long bytes, HttpStatus status, String remoteAddr) {
    
}
