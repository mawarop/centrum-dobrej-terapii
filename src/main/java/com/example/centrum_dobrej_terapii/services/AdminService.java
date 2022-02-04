package com.example.centrum_dobrej_terapii.services;

import com.example.centrum_dobrej_terapii.dtos.AppointmentRequest;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity addFreeDateAppointment(AppointmentRequest appointmentRequest);
}
