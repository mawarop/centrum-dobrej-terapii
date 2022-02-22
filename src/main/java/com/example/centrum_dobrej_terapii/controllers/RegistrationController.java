package com.example.centrum_dobrej_terapii.controllers;

import com.example.centrum_dobrej_terapii.UserRole;
import com.example.centrum_dobrej_terapii.dtos.AppUserRequest;
import com.example.centrum_dobrej_terapii.entities.AppUser;
import com.example.centrum_dobrej_terapii.services.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/registration")
@AllArgsConstructor
@CrossOrigin()
@Validated
public class RegistrationController {

    private final AppUserService appUserService;

    @PostMapping()
    public ResponseEntity register( @RequestBody AppUserRequest request) {
        boolean succesfullCreatedUser = appUserService.signUpUser(new AppUser(request, UserRole.PATIENT));
        if(succesfullCreatedUser){
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
