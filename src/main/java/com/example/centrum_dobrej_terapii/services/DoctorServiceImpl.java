package com.example.centrum_dobrej_terapii.services;

import com.example.centrum_dobrej_terapii.AppointmentStatus;
import com.example.centrum_dobrej_terapii.UserRole;
import com.example.centrum_dobrej_terapii.configs.AppointmentValidator;
import com.example.centrum_dobrej_terapii.dtos.AppointmentRequest;
import com.example.centrum_dobrej_terapii.entities.AppUser;
import com.example.centrum_dobrej_terapii.entities.Appointment;
import com.example.centrum_dobrej_terapii.repositories.AppUserRepository;
import com.example.centrum_dobrej_terapii.repositories.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor
public class DoctorServiceImpl implements DoctorService{
    final AppUserRepository appUserRepository;
    final AppointmentRepository appointmentRepository;
    final AppointmentValidator appointmentValidator;


    @Override
    public ResponseEntity addAppointment(AppointmentRequest appointmentRequest) {

        Optional<AppUser> patient = appUserRepository.findByEmail(appointmentRequest.getSecond_participant());
        AppUser principal = (AppUser) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        try {
            if (patient.isPresent()){
                boolean patientHasRolePatient = patient.get().getUserRole().name().equals(UserRole.PATIENT.name());
                Appointment appointment = new Appointment(appointmentRequest.getStart(), appointmentRequest.getEnd(),
                        principal, patient.get(), appointmentRequest.getDetails(), AppointmentStatus.FREE_DATE);
                if (patientHasRolePatient && !appointmentValidator.appointmentOverlapsDateInDatabase(appointment)) {
                    appointmentRepository.save(appointment);
                }

                else{
                    throw new IllegalStateException("Appointment overlaps another");
                }
            }
            else {
                throw new IllegalStateException("there is no patient with this email");
            }
        }
        catch (IllegalStateException illegalStateException){
            System.out.println(illegalStateException.getMessage());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
