package com.example.centrum_dobrej_terapii.exceptions;

public class AppUserNotFoundException extends RuntimeException {
    public AppUserNotFoundException(String message) {
        super(message);
    }
}
