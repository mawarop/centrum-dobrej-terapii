package com.example.centrum_dobrej_terapii.controllers;

import com.example.centrum_dobrej_terapii.dtos.RegistrationRequest;
import com.example.centrum_dobrej_terapii.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/registration")
@AllArgsConstructor

@CrossOrigin()
public class RegistrationController {

    private final RegistrationService registrationService;


    @PostMapping()
    public ResponseEntity register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);

    }



}
