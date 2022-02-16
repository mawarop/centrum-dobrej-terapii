package com.example.centrum_dobrej_terapii.services;

import com.example.centrum_dobrej_terapii.AppointmentStatus;
import com.example.centrum_dobrej_terapii.dtos.AppUserDoctorBaseResponse;
import com.example.centrum_dobrej_terapii.dtos.AppointmentResponse;

import java.util.List;

public interface PatientService {
    List<AppointmentResponse> getAppointments();
    boolean signUpFreeDateAppointment(long id);
    List<AppUserDoctorBaseResponse> getDoctorsBaseData();
    List<AppointmentResponse> getDoctorAppointmentsByAppointmentStatus(String email, AppointmentStatus appointmentStatus);
    boolean cancelAppointment(long id);
}
