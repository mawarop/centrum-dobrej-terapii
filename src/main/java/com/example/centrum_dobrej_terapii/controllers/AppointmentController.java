package com.example.centrum_dobrej_terapii.controllers;

import com.example.centrum_dobrej_terapii.UserRole;
import com.example.centrum_dobrej_terapii.dtos.AppointmentRequest;
import com.example.centrum_dobrej_terapii.entities.AppUser;
import com.example.centrum_dobrej_terapii.entities.Appointment;
import com.example.centrum_dobrej_terapii.repositories.AppUserRepository;
import com.example.centrum_dobrej_terapii.repositories.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("api/appointments")
@AllArgsConstructor
public class AppointmentController {

    final AppUserRepository appUserRepository;
    final AppointmentRepository appointmentRepository;

    @PostMapping("/add")
    public ResponseEntity addAppointment(@RequestBody AppointmentRequest appointmentRequest){
        Optional<AppUser> doctor = appUserRepository.findByEmail(appointmentRequest.getDoctor_email());
        Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        try {
            if (doctor.isPresent() && doctor.get().getUserRole().name().equals(UserRole.DOCTOR.name())) {
                Appointment appointment = new Appointment(appointmentRequest.getStart(), appointmentRequest.getEnd(), doctor.get(), (AppUser) principal);
                appointmentRepository.save(appointment);
            } else {
                throw new IllegalStateException("there is no doctor with this email");
            }
        }
        catch (IllegalStateException illegalStateException){
            System.out.println(illegalStateException.getMessage());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
