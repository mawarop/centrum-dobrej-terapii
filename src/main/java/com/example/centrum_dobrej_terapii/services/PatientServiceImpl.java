package com.example.centrum_dobrej_terapii.services;

import com.example.centrum_dobrej_terapii.dtos.AppointmentDTO;
import com.example.centrum_dobrej_terapii.entities.AppUser;
import com.example.centrum_dobrej_terapii.repositories.AppUserRepository;
import com.example.centrum_dobrej_terapii.repositories.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService{
    final AppUserRepository appUserRepository;
    final AppointmentRepository appointmentRepository;
    @Override
    public List<AppointmentDTO> getAppointments() {
        AppUser principal = (AppUser) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        List<AppointmentDTO> patientAppointments = appointmentRepository.findAppointmentAndDoctorFirstNameAndLastnameByPatientEmail(principal.getEmail());
        return patientAppointments;
    }
}
