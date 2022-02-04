package com.example.centrum_dobrej_terapii.services;

import com.example.centrum_dobrej_terapii.AppointmentStatus;
import com.example.centrum_dobrej_terapii.configs.AppointmentValidator;
import com.example.centrum_dobrej_terapii.dtos.AppointmentRequest;
import com.example.centrum_dobrej_terapii.entities.AppUser;
import com.example.centrum_dobrej_terapii.entities.Appointment;
import com.example.centrum_dobrej_terapii.repositories.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService{
    private final AppointmentRepository appointmentRepository;
    private final AppointmentValidator appointmentValidator;
    @Override
    public ResponseEntity addFreeDateAppointment(AppointmentRequest appointmentRequest) {
        AppUser principal = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Appointment appointment = new Appointment(appointmentRequest.getStart(), appointmentRequest.getEnd(),
                principal, appointmentRequest.getDetails(), AppointmentStatus.FREE_DATE);
        try {
            if (!appointmentValidator.appointmentOverlapsDateInDatabase(appointment)) {
                appointmentRepository.save(appointment);
                return new ResponseEntity(HttpStatus.CREATED);
            } else {
                throw new IllegalStateException("Appointment overlaps another");
            }
        } catch (IllegalStateException illegalStateException) {
            System.out.println(illegalStateException.getMessage());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
