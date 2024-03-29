package com.example.centrum_dobrej_terapii.services;

import com.example.centrum_dobrej_terapii.AppointmentStatus;
import com.example.centrum_dobrej_terapii.UserRole;
import com.example.centrum_dobrej_terapii.dtos.AppointmentMapper;
import com.example.centrum_dobrej_terapii.dtos.AppointmentRequest;
import com.example.centrum_dobrej_terapii.dtos.AppointmentRequestWithParticipants;
import com.example.centrum_dobrej_terapii.entities.AppUser;
import com.example.centrum_dobrej_terapii.entities.Appointment;
import com.example.centrum_dobrej_terapii.events.AppointmentEventPublisher;
import com.example.centrum_dobrej_terapii.repositories.AppUserDoctorRepository;
import com.example.centrum_dobrej_terapii.repositories.AppUserRepository;
import com.example.centrum_dobrej_terapii.repositories.AppointmentRepository;
import com.example.centrum_dobrej_terapii.util.AppointmentUtil;
import com.example.centrum_dobrej_terapii.util.beans.AppointmentValidator;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    final AppUserRepository appUserRepository;
    final AppointmentRepository appointmentRepository;
    final AppUserDoctorRepository appUserDoctorRepository;
    final AppointmentValidator appointmentValidator;
    final AppointmentMapper appointmentMapper;
    private final AppointmentEventPublisher appointmentEventPublisher;


    // patient, doctor
        @Override
    public List<Appointment> getParticipantAppointments() {
        AppUser participant = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (AppointmentUtil.isPatient(participant)) {
            return appointmentRepository.findAppointmentsByPatientEmail(participant.getEmail());
        } else if (AppointmentUtil.isDoctor(participant)) {
            return appointmentRepository.findAppointmentsByDoctorEmail(participant.getEmail());
        } else throw new IllegalStateException("Invalid user role");
    }


    // patient, doctor
    @Override
    public boolean addAppointment(AppointmentRequest appointmentRequest, AppointmentStatus appointmentStatus) {

        AppUser participant = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            if (AppointmentUtil.isDoctor(participant)) {
                Optional<AppUser> patient = appUserRepository.findByEmail(appointmentRequest.getSecond_participant());

                if (patient.isPresent()) {
                    boolean patientHasRolePatient = AppointmentUtil.isPatient(patient.get());
                    Appointment appointment = new Appointment(appointmentRequest.getStart(), appointmentRequest.getEnd(),
                            participant, patient.get(), appointmentRequest.getDetails(), appointmentStatus);
                    boolean appointmentOverlapsDateInDatabase = appointmentValidator.appointmentOverlapsDateInDatabase(appointment, UserRole.PATIENT)
                            || appointmentValidator.appointmentOverlapsDateInDatabase(appointment, UserRole.DOCTOR);
                    if (patientHasRolePatient && !appointmentOverlapsDateInDatabase) {
                        appointmentRepository.save(appointment);
                    } else {
                        throw new IllegalStateException("Appointment overlaps another");
                    }
                } else {
                    throw new IllegalStateException("There is no patient with this email");
                }
            } else if (AppointmentUtil.isPatient(participant)) {
                Optional<AppUser> doctor = appUserRepository.findByEmail(appointmentRequest.getSecond_participant());
                if (doctor.isPresent()) {
                    boolean doctorHasRoleDoctor = AppointmentUtil.isDoctor(doctor.get());
                    Appointment appointment = new Appointment(appointmentRequest.getStart(), appointmentRequest.getEnd(),
                            doctor.get(), participant, appointmentRequest.getDetails(), appointmentStatus);
                    boolean appointmentOverlapsDateInDatabase = appointmentValidator.appointmentOverlapsDateInDatabase(appointment, UserRole.PATIENT)
                            || appointmentValidator.appointmentOverlapsDateInDatabase(appointment, UserRole.DOCTOR);
                    if (doctorHasRoleDoctor && !appointmentOverlapsDateInDatabase) {
                        appointmentRepository.save(appointment);
                    } else {
                        throw new IllegalStateException("Appointment overlaps another");
                    }
                } else {
                    throw new IllegalStateException("There is no doctor with this email");
                }
            }

            else throw new IllegalStateException("Invalid user role");
        } catch (IllegalStateException illegalStateException) {
            System.out.println(illegalStateException.getMessage());
            return false;
        }


        return true;
    }

    // admin
    @Override
    public boolean addAppointment(AppointmentRequestWithParticipants appointmentRequest, AppointmentStatus appointmentStatus) {
        Optional<AppUser> patient = appointmentStatus == AppointmentStatus.FREE_DATE ? Optional.empty() : appUserRepository.findByEmail(appointmentRequest.getPatientEmail());
        Optional<AppUser> doctor = appUserRepository.findByEmail(appointmentRequest.getDoctorEmail());
        try {
            if (doctor.isPresent()) {
//                boolean patientHasRolePatient = AppointmentUtil.isPatient(patient.get());
                boolean doctorHasRoleDoctor = AppointmentUtil.isDoctor(doctor.get());
                Appointment appointment = new Appointment(appointmentRequest.getStart(), appointmentRequest.getEnd(),
                        doctor.get(), patient.isPresent() ? patient.get() : null, "", appointmentStatus);
                if (doctorHasRoleDoctor && !appointmentValidator.appointmentOverlapsDateInDatabase(appointment, UserRole.DOCTOR)) {
                    appointmentRepository.save(appointment);
                } else {
                    throw new IllegalStateException("Appointment overlaps another");
                }
            } else {
                throw new IllegalStateException("There is no patient or doctor with this email");
            }
        } catch (IllegalStateException illegalStateException) {
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
        if (isCancellationPossible) {
            appointment.get().setAppointmentStatus(AppointmentStatus.CANCELED);
            appointmentRepository.save(appointment.get());
            appointmentEventPublisher.publishAppointmentCanceledEvent(appointment.get());
            return true;
        }
        return false;
    }

    // patient
    @Override
    public boolean signUpFreeDateAppointment(long id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        AppUser principal = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        appointment.ifPresent(value -> value.setPatient(principal));
        if (appointment.isPresent() && !Optional.ofNullable(appointment.get().getPatient()).isPresent()) {
            appointment.get().setPatient(principal);
        }
        if (appointment.isPresent() && !appointmentValidator.appointmentOverlapsDateInDatabase(appointment.get(), UserRole.PATIENT)) {
            appointment.get().setPatient(principal);
            appointment.get().setAppointmentStatus(AppointmentStatus.ACCEPTED);
            appointmentRepository.save(appointment.get());
            appointmentEventPublisher.publishAppointmentSignedUpEvent(appointment.get());
            return true;
        }
        return false;
    }

    // patient, doctor
    @Override
    public List<Appointment> getUserAppointmentsByUserEmailAndAppointmentStatus(String email, AppointmentStatus appointmentStatus) {
        Optional<AppUser> user = appUserRepository.findByEmail(email);
        if (user.isPresent()) {
            if (AppointmentUtil.isDoctor(user.get())) {
                return appointmentRepository.findAppointmentsByDoctorEmailAndAppointmentStatus(email, appointmentStatus);
            } else if (AppointmentUtil.isPatient(user.get())) {
                return appointmentRepository.findAppointmentsByPatientEmailAndAppointmentStatus(email, appointmentStatus);
            } else throw new IllegalStateException("Invalid user role");
        } else throw new IllegalStateException("User not exist");
    }

    @Override
    public boolean updateAppointment(long id, AppointmentRequest appointmentRequest) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        try {
            if (optionalAppointment.isPresent()) {
                Appointment appointment = optionalAppointment.get();
                appointmentMapper.updateAppointmentFromAppointmentRequest(appointmentRequest, appointment);
                appointmentRepository.save(appointment);
            } else {
                throw new IllegalStateException("Appointment not exist");
            }
        } catch (IllegalStateException illegalStateException) {
            System.out.println(illegalStateException.getMessage());
            return false;
        }
        return true;
    }


    @Override
    @Transactional
    public boolean changeAppointment(long appointmentIdToCancel, long freeDateAppointmentId) {
        if (!this.cancelAppointment(appointmentIdToCancel))
            return false;
        return this.signUpFreeDateAppointment(freeDateAppointmentId);
    }
}

