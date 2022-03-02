package com.example.centrum_dobrej_terapii.services;

import com.example.centrum_dobrej_terapii.AppointmentStatus;
import com.example.centrum_dobrej_terapii.UserRole;
import com.example.centrum_dobrej_terapii.configs.AppointmentValidator;
import com.example.centrum_dobrej_terapii.dtos.AppointmentMapper;
import com.example.centrum_dobrej_terapii.dtos.AppointmentRequest;
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
public class AppointmentServiceImpl implements AppointmentService{
    final AppUserRepository appUserRepository;
    final AppointmentRepository appointmentRepository;
    final AppUserDoctorRepository appUserDoctorRepository;
    final AppointmentValidator appointmentValidator;
    final AppointmentMapper appointmentMapper;

    boolean isDoctor(AppUser user){
        return user.getUserRole().name().equals(UserRole.DOCTOR.name());
    }
    boolean isPatient(AppUser user){
        return user.getUserRole().name().equals(UserRole.PATIENT.name());
    }
    boolean isAdmin(AppUser user){
        return user.getUserRole().name().equals(UserRole.ADMIN.name());
    }


    // patient, doctor
    @Override
    public List<Appointment> getParticipantAppointments() {
        AppUser participant = (AppUser) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        if(isPatient(participant)){
            List<Appointment> patientAppointments =
                    appointmentRepository.findAppointmentByPatientEmail(participant.getEmail());
            return patientAppointments;
        }
        else if(isDoctor(participant)){
            List<Appointment> doctorAppointments = appointmentRepository.findAppointmentByDoctorEmail(participant.getEmail());
            return doctorAppointments;
        }
        else throw new IllegalStateException("Invalid user role");
    }


    // patient, doctor
    @Override
    public boolean addAppointment(AppointmentRequest appointmentRequest, AppointmentStatus appointmentStatus) {

        AppUser participant = (AppUser) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();

        try {
        if(isDoctor(participant)){
            Optional<AppUser> patient = appUserRepository.findByEmail(appointmentRequest.getSecond_participant());

            if (patient.isPresent()){
                boolean patientHasRolePatient = isPatient(patient.get());
                Appointment appointment = new Appointment(appointmentRequest.getStart(), appointmentRequest.getEnd(),
                        participant, patient.get(), appointmentRequest.getDetails(), appointmentStatus);
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

        else if(isPatient(participant)){
                Optional<AppUser> doctor = appUserRepository.findByEmail(appointmentRequest.getSecond_participant());
                if (doctor.isPresent()){
                    boolean doctorHasRoleDoctor = isDoctor(doctor.get());
                    Appointment appointment = new Appointment(appointmentRequest.getStart(), appointmentRequest.getEnd(),
                            doctor.get(), participant, appointmentRequest.getDetails(), appointmentStatus);
                    if (doctorHasRoleDoctor && !appointmentValidator.appointmentOverlapsDateInDatabase(appointment)) {
                        appointmentRepository.save(appointment);
                    }
                    else{
                        throw new IllegalStateException("Appointment overlaps another");
                    }
                }
                else {
                    throw new IllegalStateException("There is no patient with this email");
                }
            }

        else if(isAdmin(participant)) throw new IllegalStateException("Admin cant create appointment with this parameters");

        else throw new IllegalStateException("Invalid user role");
        }

        catch (IllegalStateException illegalStateException){
            System.out.println(illegalStateException.getMessage());
            return false;
        }

        return true;
    }

    //all
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

    // patient
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

    // patient, doctor
    @Override
    public List<Appointment> getUserAppointmentsByUserEmailAndAppointmentStatus(String email, AppointmentStatus appointmentStatus) {
        Optional<AppUser> user = appUserRepository.findByEmail(email);
        if (user.isPresent()) {
            if (isDoctor(user.get())) {
                return appointmentRepository.findAppointmentsByDoctorEmailAndAppointmentStatus(email, appointmentStatus);
            } else if (isPatient(user.get())) {
                return appointmentRepository.findAppointmentsByPatientEmailAndAppointmentStatus(email, appointmentStatus);
            } else throw new IllegalStateException("Invalid user role");
        } else throw new IllegalStateException("Invalid user role");
    }

    @Override
    public boolean updateAppointment(long id, AppointmentRequest appointmentRequest) {
        Optional<Appointment> optionalAppointment =appointmentRepository.findById(id);
        try {
            if (optionalAppointment.isPresent()) {
                Appointment appointment = optionalAppointment.get();
                appointmentMapper.updateAppointmentFromAppointmentRequest(appointmentRequest, appointment);
                appointmentRepository.save(appointment);
            } else {
                throw new IllegalStateException("User not find");
            }
        }catch (IllegalStateException illegalStateException)
        {
            System.out.println(illegalStateException.getMessage());
            return false;
        }
            return true;
        }

    @Override
    public boolean changeAppointment(long appointmentIdToCancel, long freeDateAppointmentId) {
        System.out.println("ToCancleId: " + appointmentIdToCancel + " FreeDateId: " +freeDateAppointmentId);;
        if (!this.cancelAppointment(appointmentIdToCancel))
            return false;
        return this.signUpFreeDateAppointment(freeDateAppointmentId);
    }
}

