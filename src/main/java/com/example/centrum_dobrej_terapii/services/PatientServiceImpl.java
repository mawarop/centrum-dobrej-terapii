package com.example.centrum_dobrej_terapii.services;

import com.example.centrum_dobrej_terapii.AppointmentStatus;
import com.example.centrum_dobrej_terapii.configs.AppointmentValidator;
import com.example.centrum_dobrej_terapii.dtos.AppUserDoctorBaseResponse;
import com.example.centrum_dobrej_terapii.dtos.AppointmentResponse;
import com.example.centrum_dobrej_terapii.entities.AppUser;
import com.example.centrum_dobrej_terapii.entities.Appointment;
import com.example.centrum_dobrej_terapii.repositories.AppUserDoctorRepository;
import com.example.centrum_dobrej_terapii.repositories.AppUserRepository;
import com.example.centrum_dobrej_terapii.repositories.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService{
    final AppUserRepository appUserRepository;
    final AppointmentRepository appointmentRepository;
    final AppUserDoctorRepository appUserDoctorRepository;
    final AppointmentValidator appointmentValidator;
    @Override
    public List<AppointmentResponse> getAppointments() {
        AppUser principal = (AppUser) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        List<AppointmentResponse> patientAppointments =
                appointmentRepository.findAppointmentAndDoctorFirstNameAndLastnameByPatientEmail(principal.getEmail());
        return patientAppointments;
    }

    @Override
    public boolean signUpFreeDateAppointment(long id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if(appointment.isPresent()){
            AppUser principal = (AppUser) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        appointment.get().setPatient(principal);
        appointment.get().setAppointmentStatus(AppointmentStatus.ACCEPTED);
        appointmentRepository.save(appointment.get());
        return true;
    }
    return false;
    }

    @Override
    public List<AppUserDoctorBaseResponse> getDoctorsBaseData() {
        List<AppUserDoctorBaseResponse> appUserDoctorBaseResponseList = appUserDoctorRepository.findAppUserDoctorBaseData();
        return appUserDoctorBaseResponseList;
    }

    @Override
    public List<AppointmentResponse> getDoctorAppointmentsByAppointmentStatus(String email, AppointmentStatus appointmentStatus) {
        return appointmentRepository.findAppointmentsByDoctorEmailAndAppointmentStatus(email, appointmentStatus);
    }

    @Override
    public boolean cancelAppointment(long id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        boolean isCancellationPossible = appointmentValidator.isAppointmentCancellationPossible(appointment);
        if(isCancellationPossible){
            appointment.get().setAppointmentStatus(AppointmentStatus.CANCELED);
            appointmentRepository.save(appointment.get());
            return true;
        }
        return false;
    }
}
