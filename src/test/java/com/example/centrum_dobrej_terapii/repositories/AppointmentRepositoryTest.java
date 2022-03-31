package com.example.centrum_dobrej_terapii.repositories;

import com.example.centrum_dobrej_terapii.AppointmentStatus;
import com.example.centrum_dobrej_terapii.UserRole;
import com.example.centrum_dobrej_terapii.entities.AppUser;
import com.example.centrum_dobrej_terapii.entities.Appointment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class AppointmentRepositoryTest {
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private AppointmentRepository appointmentRepositoryUnderTest;


    @AfterEach
    void tearDown() {
        appointmentRepositoryUnderTest.deleteAll();
        appUserRepository.deleteAll();
    }

    @Test
    void should_FindAppointmentsByPatientEmail_When_EmailExist() {
        // given
        List<AppUser> participants = createParticipants();
        saveParticipantsInDb(participants);
        List<Appointment> appointments = createAppointments(participants);
        saveAppointmentsInDb(appointments);
        String patientEmail = participants.get(0).getEmail();

        // when
        List<Appointment> expected = appointmentRepositoryUnderTest
                .findAppointmentsByPatientEmail(patientEmail);
        // then
        assertThat(expected).isEqualTo(appointments);
    }
    @Test
    void should_NotFindAppointmentsByPatientEmail_When_EmailNotExist() {
        // given
        String patientEmail = "patient@gmail.com";
        // when
        List<Appointment> expected = appointmentRepositoryUnderTest
                .findAppointmentsByPatientEmail(patientEmail);
        // then
        Assertions.assertThat(expected).isEmpty();
    }

    @Test
    void should_FindAppointmentsByDoctorEmail_WhenEmailExist() {
        // given
        List<AppUser> participants = createParticipants();
        saveParticipantsInDb(participants);
        List<Appointment> appointments = createAppointments(participants);
        saveAppointmentsInDb(appointments);
        String doctorEmail = participants.get(1).getEmail();

        // when
        List<Appointment> expected = appointmentRepositoryUnderTest
                .findAppointmentsByDoctorEmail(doctorEmail);
        // then
        assertThat(expected).isEqualTo(appointments);
    }

    @Test
    void should_NotFindAppointmentsByDoctorEmail_When_EmailNotExist() {
        // given
        String doctorEmail = "doctor@gmail.com";
        // when
        List<Appointment> expected = appointmentRepositoryUnderTest
                .findAppointmentsByDoctorEmail(doctorEmail);
        // then
        Assertions.assertThat(expected).isEmpty();
    }

    @Test
    void should_FindAppointmentsByDoctorEmailAndAppointmentStatus_When_EmailAndAppointmentStatusExist() {
        // given
        List<AppUser> participants = createParticipants();
        saveParticipantsInDb(participants);
        List<Appointment> appointments = createAppointments(participants);
        saveAppointmentsInDb(appointments);
        String doctorEmail = participants.get(1).getEmail();

        // when
        List<Appointment> expected = appointmentRepositoryUnderTest
                .findAppointmentsByDoctorEmailAndAppointmentStatus(doctorEmail, AppointmentStatus.ACCEPTED);
        // then
        assertThat(expected).isEqualTo(appointments);
    }

    @Test
    void should_NotFindAppointmentsByDoctorEmailAndAppointmentStatus_When_EmailOrAppointmentStatusNotExist(){
        // given
        String doctorEmail = "doctor@gmail.com";
        // when
        List<Appointment> expected = appointmentRepositoryUnderTest
                .findAppointmentsByDoctorEmailAndAppointmentStatus(doctorEmail, AppointmentStatus.ACCEPTED);
        // then
        Assertions.assertThat(expected).isEmpty();
    }

    @Test
    void should_findAppointmentsByPatientEmailAndAppointmentStatus_When_EmailAndAppointmentStatusExist() {
        // given
        List<AppUser> participants = createParticipants();
        saveParticipantsInDb(participants);
        List<Appointment> appointments = createAppointments(participants);
        saveAppointmentsInDb(appointments);
        String patientEmail = participants.get(0).getEmail();

        // when
        List<Appointment> expected = appointmentRepositoryUnderTest
                .findAppointmentsByPatientEmailAndAppointmentStatus(patientEmail, AppointmentStatus.ACCEPTED);
        // then
        assertThat(expected).isEqualTo(appointments);
    }


    @Test
    void should_NotFindAppointmentsByPatientEmailAndAppointmentStatus_When_EmailOrAppointmentStatusNotExist() {
        // given
        String patientEmail = "patient@gmail.com";
        // when
        List<Appointment> expected = appointmentRepositoryUnderTest
                .findAppointmentsByPatientEmailAndAppointmentStatus(patientEmail, AppointmentStatus.ACCEPTED);
        // then
        Assertions.assertThat(expected).isEmpty();
    }

    private AppUser createExamplePatient(){
        return new AppUser(
                "pkowal",
                "qwerty",
                "77121727497",
                "Piotr",
                "Kowalski",
                "patient@gmail.com",
                "736346373",
                UserRole.PATIENT,
                false,
                false);
    }

    private AppUser createExampleDoctor(){
        return new AppUser(
                "pkowal",
                "qwerty123",
                "60120215391",
                "Sebastian",
                "Nowak",
                "doctor@gmail.com",
                "745745684",
                UserRole.DOCTOR,
                false,
                false);
    }

    private Appointment createExampleAppointmentNumbOne(AppUser patient, AppUser doctor){
        return new Appointment(LocalDateTime.of(2022, Month.DECEMBER, 20, 18, 0),
                LocalDateTime.of(2022, Month.DECEMBER, 20, 19, 0),
                doctor, patient, "test details1", AppointmentStatus.ACCEPTED);
    }

    private Appointment createExampleAppointmentNumbTwo(AppUser patient, AppUser doctor){
        return new Appointment(LocalDateTime.of(2022, Month.DECEMBER, 20, 20, 0),
                LocalDateTime.of(2022, Month.DECEMBER, 20, 21, 0),
                doctor, patient, "test details2", AppointmentStatus.ACCEPTED);
    }

    private List<AppUser> createParticipants(){
        AppUser patient = createExamplePatient();
        AppUser doctor = createExampleDoctor();
        return List.of(patient, doctor);
    }

    private List<Appointment> createAppointments(List<AppUser> participants){
        Appointment appointmentNumberOne = createExampleAppointmentNumbOne(participants.get(0), participants.get(1));
        Appointment appointmentNumberTwo = createExampleAppointmentNumbTwo(participants.get(0), participants.get(1));
        return List.of(appointmentNumberOne, appointmentNumberTwo);
    }

    private void saveParticipantsInDb(List<AppUser> participants){
        appUserRepository.saveAll(participants);
    }

    private void saveAppointmentsInDb(List<Appointment> appointments){
        appointmentRepositoryUnderTest.saveAll(appointments);
    }


}