package com.example.centrum_dobrej_terapii.services;

import com.example.centrum_dobrej_terapii.AppointmentStatus;
import com.example.centrum_dobrej_terapii.dtos.AppointmentRequest;
import com.example.centrum_dobrej_terapii.entities.Appointment;

import java.util.List;

public interface AppointmentService {
    List<Appointment> getParticipantAppointments();
    boolean addAppointment(AppointmentRequest appointmentRequest, AppointmentStatus appointmentStatus);
    boolean cancelAppointment(long id);
    boolean signUpFreeDateAppointment(long id);
    List<Appointment> getUserAppointmentsByUserEmailAndAppointmentStatus(String email, AppointmentStatus appointmentStatus);
    boolean updateAppointment(long id, AppointmentRequest appointmentRequest);
    boolean changeAppointment(long appointmentIdToCancel, long freeDateAppointmentId);

}
