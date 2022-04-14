package com.example.centrum_dobrej_terapii.services;

public interface EmailSender {
    void send(String to, String subject, String email);
}
