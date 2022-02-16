package com.example.centrum_dobrej_terapii.services;

import com.example.centrum_dobrej_terapii.dtos.AppointmentRequest;

public interface AdminService {
    boolean addFreeDateAppointment(AppointmentRequest appointmentRequest);
}
