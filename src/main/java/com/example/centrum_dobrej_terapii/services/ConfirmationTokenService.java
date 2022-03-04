package com.example.centrum_dobrej_terapii.services;

import com.example.centrum_dobrej_terapii.entities.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenService {
    void saveConfirmationToken(ConfirmationToken token);
    Optional<ConfirmationToken> getToken(String token);
    int setConfirmedDateTime(String token);
}
