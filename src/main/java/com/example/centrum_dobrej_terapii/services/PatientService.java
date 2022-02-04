package com.example.centrum_dobrej_terapii.services;

import com.example.centrum_dobrej_terapii.dtos.AppointmentDTO;

import java.util.List;

public interface PatientService {
    List<AppointmentDTO> getAppointments();
}
