package com.example.centrum_dobrej_terapii.services;

import com.example.centrum_dobrej_terapii.entities.ConfirmationToken;
import com.example.centrum_dobrej_terapii.repositories.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }

    @Override
    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    @Override
    public int setConfirmedDateTime(String token) {
        return confirmationTokenRepository.updateConfirmedDate(token, LocalDateTime.now());
    }
}
