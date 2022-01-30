package com.example.centrum_dobrej_terapii.services;

import com.example.centrum_dobrej_terapii.UserRole;
import com.example.centrum_dobrej_terapii.dtos.RegistrationRequest;
import com.example.centrum_dobrej_terapii.entities.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final EmailValidator emailValidator;
    private final AppUserService appUserService;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException("email not valid");
        }
        return appUserService.signUpUser(new AppUser(request, UserRole.DOCTOR));
    }
}
