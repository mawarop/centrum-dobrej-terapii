package com.example.centrum_dobrej_terapii.services;

import com.example.centrum_dobrej_terapii.dtos.AppointmentRequest;
import com.example.centrum_dobrej_terapii.dtos.AppointmentResponse;

import java.util.List;

public interface DoctorService {
    boolean addAppointment(AppointmentRequest appointmentRequest);
    List<AppointmentResponse> getAppointments();
    boolean cancelAppointment(long id);
//    List<AppUserBaseResponse> getPatients();
}
