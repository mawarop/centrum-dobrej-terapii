package com.example.centrum_dobrej_terapii.services;

import com.example.centrum_dobrej_terapii.AppointmentStatus;
import com.example.centrum_dobrej_terapii.UserRole;
import com.example.centrum_dobrej_terapii.data.AbstractTestDataFactory;
import com.example.centrum_dobrej_terapii.data.AbstractTestDataFactoryProvider;
import com.example.centrum_dobrej_terapii.dtos.AppointmentMapper;
import com.example.centrum_dobrej_terapii.dtos.AppointmentRequest;
import com.example.centrum_dobrej_terapii.entities.AppUser;
import com.example.centrum_dobrej_terapii.entities.Appointment;
import com.example.centrum_dobrej_terapii.events.AppointmentEventPublisher;
import com.example.centrum_dobrej_terapii.repositories.AppUserDoctorRepository;
import com.example.centrum_dobrej_terapii.repositories.AppUserRepository;
import com.example.centrum_dobrej_terapii.repositories.AppointmentRepository;
import com.example.centrum_dobrej_terapii.util.beans.AppointmentValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplTest {

    @Spy private AppointmentService appointmentServiceUnderTest;


    @Mock private AppUserRepository appUserRepository;
    @Mock private AppointmentRepository appointmentRepository;
    @Mock private AppUserDoctorRepository appUserDoctorRepository;
    @Mock
    private AppointmentValidator appointmentValidator;
    @Mock
    private AppointmentEventPublisher appointmentEventPublisher;
    @Spy
    private AppointmentMapper appointmentMapper;

    @BeforeEach
    void beforeAll() {
        appointmentMapper = Mappers.getMapper(AppointmentMapper.class);
        appointmentServiceUnderTest = spy(new AppointmentServiceImpl(appUserRepository, appointmentRepository,
                appUserDoctorRepository, appointmentValidator, appointmentMapper, appointmentEventPublisher));
//        serviceSpy = spy(appointmentServiceUnderTest);
    }

    @AfterEach
    void tearDown() {
        appointmentRepository.deleteAll();
        appUserRepository.deleteAll();
    }

    @Test
    @WithMockAppUser(email = "p@gmail.com",userRole = UserRole.PATIENT)
    void can_GetParticipantAppointments_When_RoleIsPatient() {
        // given
        final String TESTED_PATIENT_EMAIL = "p@gmail.com";
        // when
        appointmentServiceUnderTest.getParticipantAppointments();
        // then
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(appointmentRepository).findAppointmentsByPatientEmail(argumentCaptor.capture());
        String capturedEmail = argumentCaptor.getValue();
        assertThat(capturedEmail).isEqualTo(TESTED_PATIENT_EMAIL);
    }

    @Test
    @WithMockAppUser(email = "d@gmail.com",userRole = UserRole.DOCTOR)
    void can_GetParticipantAppointments_When_RoleIsDoctor() {
        // given
        final String TESTED_DOCTOR_EMAIL = "d@gmail.com";
        // when
        appointmentServiceUnderTest.getParticipantAppointments();
        // then
        ArgumentCaptor<String> emailArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(appointmentRepository).findAppointmentsByDoctorEmail(emailArgumentCaptor.capture());
        String capturedEmail = emailArgumentCaptor.getValue();
        assertThat(capturedEmail).isEqualTo(TESTED_DOCTOR_EMAIL);
    }

    @Test
    @WithMockAppUser(email = "a@gmail.com",userRole = UserRole.ADMIN)
    void can_Not_GetParticipantAppointments_When_RoleIsAdmin() {
        // given
        final String TESTED_DOCTOR_EMAIL = "a@gmail.com";
        // when
        // then
        assertThatThrownBy(() -> appointmentServiceUnderTest.getParticipantAppointments())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Invalid user role");
    }

    @Test
    @WithMockAppUser(email = "p@gmail.com",userRole = UserRole.PATIENT)
    void can_AddAppointment_When_ParticipantRoleIsPatient() {
        // given
        AbstractTestDataFactory<AppUser, UserRole> abstractTestDataFactory = AbstractTestDataFactoryProvider.getAbstractFactory(AppUser.class);
        AppUser doctor = (AppUser) abstractTestDataFactory.create(UserRole.DOCTOR);
        String doctorEmail = doctor.getEmail();
        LocalDateTime start = LocalDateTime.of(2022, Month.FEBRUARY, 20, 19, 0);
        LocalDateTime end = LocalDateTime.of(2022, Month.FEBRUARY, 20, 20, 0);
        AppointmentRequest appointmentRequest = new AppointmentRequest(start, end, "details",
                doctorEmail);
        given(appUserRepository.findByEmail(doctorEmail)).willReturn(Optional.of(doctor));
        // when
        appointmentServiceUnderTest.addAppointment(appointmentRequest, AppointmentStatus.ACCEPTED);
        // then
        ArgumentCaptor<String> emailArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Appointment> appointmentArgumentCaptor = ArgumentCaptor.forClass(Appointment.class);
        verify(appUserRepository).findByEmail(emailArgumentCaptor.capture());
        verify(appointmentRepository).save(appointmentArgumentCaptor.capture());
        assertThat(emailArgumentCaptor.getValue()).isEqualTo(appointmentArgumentCaptor.getValue().getDoctor().getEmail());

    }

    @Test
    @WithMockAppUser(email = "d@gmail.com",userRole = UserRole.DOCTOR)
    void can_AddAppointment_When_ParticipantRoleIsDoctor() {
        // given
        AbstractTestDataFactory<AppUser, UserRole> abstractTestDataFactory = AbstractTestDataFactoryProvider.getAbstractFactory(AppUser.class);
        AppUser patient = (AppUser) abstractTestDataFactory.create(UserRole.PATIENT);
        String patientEmail = patient.getEmail();
        LocalDateTime start = LocalDateTime.of(2022, Month.FEBRUARY, 20, 19, 0);
        LocalDateTime end = LocalDateTime.of(2022, Month.FEBRUARY, 20, 20, 0);
        AppointmentRequest appointmentRequest = new AppointmentRequest(start, end, "details",
                patientEmail);
        given(appUserRepository.findByEmail(patientEmail)).willReturn(Optional.of(patient));
        // when
        appointmentServiceUnderTest.addAppointment(appointmentRequest, AppointmentStatus.ACCEPTED);
        // then
        ArgumentCaptor<String> emailArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Appointment> appointmentArgumentCaptor = ArgumentCaptor.forClass(Appointment.class);
        verify(appUserRepository).findByEmail(emailArgumentCaptor.capture());
        verify(appointmentRepository).save(appointmentArgumentCaptor.capture());
        assertThat(emailArgumentCaptor.getValue()).isEqualTo(appointmentArgumentCaptor.getValue().getPatient().getEmail());
    }

//    @Test
//    @WithMockAppUser(email = "a@gmail.com",userRole = UserRole.ADMIN)
//    void can_AddAppointment_When_ParticipantRoleIsAdmin() {
//        // given
//        AbstractTestDataFactory<AppUser, UserRole> abstractTestDataFactory = AbstractTestDataFactoryProvider.getAbstractFactory(AppUser.class);
//        AppUser patient = (AppUser) abstractTestDataFactory.create(UserRole.PATIENT);
//        String patientEmail = patient.getEmail();
//        LocalDateTime start = LocalDateTime.of(2022, Month.FEBRUARY, 20, 19, 0);
//        LocalDateTime end = LocalDateTime.of(2022, Month.FEBRUARY, 20, 20, 0);
//        AppointmentRequest appointmentRequest = new AppointmentRequest(start, end, "details",
//                patientEmail);
//        given(appUserRepository.findByEmail(patientEmail)).willReturn(Optional.of(patient);
//        // when
//        // then
//        assertThatThrownBy(() -> appointmentServiceUnderTest.addAppointment(appointmentRequest, AppointmentStatus.ACCEPTED))
//                .isInstanceOf(IllegalStateException.class)
//                .hasMessage("Invalid user role");
//    }


    @Test
    void can_CancelAppointment_When_IsPossible() {
        // given
        AppUser patient = new AppUser(
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
        AppUser doctor = new AppUser(
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
        Appointment appointment = new Appointment(
                LocalDateTime.of(2022, Month.DECEMBER, 20, 18, 0),
                LocalDateTime.of(2022, Month.DECEMBER, 20, 19, 0),
                doctor,
                patient,
                "test details1",
                AppointmentStatus.ACCEPTED);
        long appointmentId = appointment.getId();
        given(appointmentRepository.findById(appointmentId)).willReturn(Optional.of(appointment));
        given(appointmentValidator.isAppointmentCancellationPossible(Optional.of(appointment))).willReturn(true);
        // when
        appointmentServiceUnderTest.cancelAppointment(appointmentId);
        // then
        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Appointment> appointmentArgumentCaptor = ArgumentCaptor.forClass(Appointment.class);
        ArgumentCaptor<Optional<Appointment>> optionalAppointmentArgumentCaptor = ArgumentCaptor.forClass(Optional.class);
        verify(appointmentRepository).findById(idArgumentCaptor.capture());
        verify(appointmentValidator).isAppointmentCancellationPossible(optionalAppointmentArgumentCaptor.capture());
        verify(appointmentRepository).save(appointmentArgumentCaptor.capture());
        assertThat(idArgumentCaptor.getValue()).isEqualTo(appointmentId);
        assertThat(appointmentArgumentCaptor.getValue()).isEqualTo(appointment);
        assertThat(optionalAppointmentArgumentCaptor.getValue()).isEqualTo(Optional.of(appointment));
    }

    @Test
    void can_Not_CancelAppointment_When_IsNotPossible() {
        // given
        AppUser patient = new AppUser(
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
        AppUser doctor = new AppUser(
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
        Appointment appointment = new Appointment(
                LocalDateTime.of(2022, Month.FEBRUARY, 20, 18, 0),
                LocalDateTime.of(2022, Month.FEBRUARY, 20, 19, 0),
                doctor,
                patient,
                "test details1",
                AppointmentStatus.ACCEPTED);
        long appointmentId = appointment.getId();
        given(appointmentRepository.findById(appointmentId)).willReturn(Optional.of(appointment));
        given(appointmentValidator.isAppointmentCancellationPossible(Optional.of(appointment))).willReturn(false);
        // when
        appointmentServiceUnderTest.cancelAppointment(appointmentId);
        // then
        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Appointment> appointmentArgumentCaptor = ArgumentCaptor.forClass(Appointment.class);
        ArgumentCaptor<Optional<Appointment>> optionalAppointmentArgumentCaptor = ArgumentCaptor.forClass(Optional.class);
        verify(appointmentRepository).findById(idArgumentCaptor.capture());
        verify(appointmentValidator).isAppointmentCancellationPossible(optionalAppointmentArgumentCaptor.capture());
        assertThat(idArgumentCaptor.getValue()).isEqualTo(appointmentId);
        assertThat(optionalAppointmentArgumentCaptor.getValue()).isEqualTo(Optional.of(appointment));
    }

    @Test
    @WithMockAppUser(email = "d@gmail.com",userRole = UserRole.DOCTOR)
    void can_signUpFreeDateAppointment_When_AppointmentExist() {
        // given
        AppUser patient = new AppUser(
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
        AppUser doctor = new AppUser(
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
        Appointment appointment = new Appointment(
                LocalDateTime.of(2022, Month.FEBRUARY, 20, 18, 0),
                LocalDateTime.of(2022, Month.FEBRUARY, 20, 19, 0),
                doctor,
                patient,
                "test details1",
                AppointmentStatus.ACCEPTED);
        long appointmentId = appointment.getId();
        given(appointmentRepository.findById(appointmentId)).willReturn(Optional.of(appointment));
        // when
        appointmentServiceUnderTest.signUpFreeDateAppointment(appointmentId);
        // then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Appointment> appointmentArgumentCaptor = ArgumentCaptor.forClass(Appointment.class);
        verify(appointmentRepository).findById(longArgumentCaptor.capture());
        verify(appointmentRepository).save(appointmentArgumentCaptor.capture());
        assertThat(appointmentId).isEqualTo(longArgumentCaptor.getValue());
        assertThat(appointment).isEqualTo(appointmentArgumentCaptor.getValue());
    }

    @Test
    @WithMockAppUser(email = "d@gmail.com",userRole = UserRole.DOCTOR)
    void can_Not_signUpFreeDateAppointment_When_AppointmentNotExist(){
        // given
        long appointmentId = 0;
        // when
        boolean expected = appointmentServiceUnderTest.signUpFreeDateAppointment(appointmentId);
        // then
        assertThat(expected).isFalse();
    }

    @Test
    void can_getUserAppointmentsByUserEmailAndAppointmentStatus_When_UserRoleIsDoctor() {
        AppUser patient = new AppUser(
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
        AppUser doctor = new AppUser(
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
        Appointment appointmentNumbOne = new Appointment(
                LocalDateTime.of(2022, Month.FEBRUARY, 20, 18, 0),
                LocalDateTime.of(2022, Month.FEBRUARY, 20, 19, 0),
                doctor,
                patient,
                "test details1",
                AppointmentStatus.ACCEPTED);
        Appointment appointmentNumbTwo = new Appointment(
                LocalDateTime.of(2022, Month.FEBRUARY, 22, 18, 0),
                LocalDateTime.of(2022, Month.FEBRUARY, 22, 19, 0),
                doctor,
                patient,
                "test details2",
                AppointmentStatus.ACCEPTED);
        String doctorEmail = doctor.getEmail();
        given(appUserRepository.findByEmail(doctorEmail)).willReturn(Optional.of(doctor));
        given(appointmentRepository.findAppointmentsByDoctorEmailAndAppointmentStatus(doctorEmail, AppointmentStatus.ACCEPTED))
                .willReturn(List.of(appointmentNumbOne,appointmentNumbTwo));
        // when
        List<Appointment> expected = appointmentServiceUnderTest.getUserAppointmentsByUserEmailAndAppointmentStatus(doctorEmail, AppointmentStatus.ACCEPTED);
        // then
        assertThat(expected).isEqualTo(List.of(appointmentNumbOne, appointmentNumbTwo));

    }

    @Test
    void can_getUserAppointmentsByUserEmailAndAppointmentStatus_When_UserRoleIsPatient() {
        AppUser patient = new AppUser(
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
        AppUser doctor = new AppUser(
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
        Appointment appointmentNumbOne = new Appointment(
                LocalDateTime.of(2022, Month.FEBRUARY, 20, 18, 0),
                LocalDateTime.of(2022, Month.FEBRUARY, 20, 19, 0),
                doctor,
                patient,
                "test details1",
                AppointmentStatus.ACCEPTED);
        Appointment appointmentNumbTwo = new Appointment(
                LocalDateTime.of(2022, Month.FEBRUARY, 22, 18, 0),
                LocalDateTime.of(2022, Month.FEBRUARY, 22, 19, 0),
                doctor,
                patient,
                "test details2",
                AppointmentStatus.ACCEPTED);
        String patientEmail = patient.getEmail();
        given(appUserRepository.findByEmail(patientEmail)).willReturn(Optional.of(patient));
        given(appointmentRepository.findAppointmentsByPatientEmailAndAppointmentStatus(patientEmail, AppointmentStatus.ACCEPTED))
                .willReturn(List.of(appointmentNumbOne,appointmentNumbTwo));
        // when
        List<Appointment> expected = appointmentServiceUnderTest.getUserAppointmentsByUserEmailAndAppointmentStatus(patientEmail, AppointmentStatus.ACCEPTED);
        // then
        assertThat(expected).isEqualTo(List.of(appointmentNumbOne, appointmentNumbTwo));

    }

    @Test
    void can_Not_getUserAppointmentsByUserEmailAndAppointmentStatus_When_UserRoleIsAdmin() {
        // given
        AppUser admin = new AppUser(
                "akowal",
                "qwerty",
                "77121727497",
                "Piotr",
                "Kowalski",
                "patient@gmail.com",
                "736346373",
                UserRole.ADMIN,
                false,
                false);
        String adminEmail =  admin.getEmail();
        given(appUserRepository.findByEmail(adminEmail)).willReturn(Optional.of(admin));
        // when
        // then
        assertThatThrownBy(() -> appointmentServiceUnderTest.getUserAppointmentsByUserEmailAndAppointmentStatus(adminEmail, AppointmentStatus.ACCEPTED))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Invalid user role");

    }

    @Test
    void can_Not_getUserAppointmentsByUserEmailAndAppointmentStatus_When_UserNotExist() {
        // given

        String userEmail =  "userEmail@gmail.com";
        given(appUserRepository.findByEmail(userEmail)).willReturn(Optional.empty());
        // when
        // then
        assertThatThrownBy(() -> appointmentServiceUnderTest.getUserAppointmentsByUserEmailAndAppointmentStatus(userEmail, AppointmentStatus.ACCEPTED))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("User not exist");
    }

    @Test
    void can_updateAppointment_When_AppointmentExist() {
        // given
        AppUser patient = new AppUser(
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
        AppUser doctor = new AppUser(
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
        Appointment appointment = new Appointment(
                LocalDateTime.of(2022, Month.FEBRUARY, 20, 18, 0),
                LocalDateTime.of(2022, Month.FEBRUARY, 20, 19, 0),
                doctor,
                patient,
                "test details1",
                AppointmentStatus.ACCEPTED);

        long appointmentId = appointment.getId();
        AppointmentRequest appointmentRequest = new AppointmentRequest(LocalDateTime.of(2022, Month.FEBRUARY, 22, 18, 0),
                LocalDateTime.of(2022, Month.FEBRUARY, 22, 19, 0),"updated details", null);
        given(appointmentRepository.findById(appointmentId)).willReturn(Optional.of(appointment));
        Appointment appointmentAfterUpdate = new Appointment(
                LocalDateTime.of(2022, Month.FEBRUARY, 22, 18, 0),
                LocalDateTime.of(2022, Month.FEBRUARY, 22, 19, 0),
                doctor,
                patient,
                "updated details",
                AppointmentStatus.ACCEPTED);

        // when
//        doCallRealMethod().when(appointmentMapper).updateAppointmentFromAppointmentRequest(appointmentRequest, appointment);
        boolean expected = appointmentServiceUnderTest.updateAppointment(appointmentId, appointmentRequest);
        // then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Appointment> appointmentArgumentCaptor = ArgumentCaptor.forClass(Appointment.class);
        verify(appointmentRepository).findById(longArgumentCaptor.capture());
        verify(appointmentRepository).save(appointmentArgumentCaptor.capture());
        assertThat(expected).isTrue();
        assertThat(longArgumentCaptor.getValue()).isEqualTo(appointmentId);
        assertThat(appointmentArgumentCaptor.getValue()).isEqualTo(appointmentAfterUpdate);
    }

    @Test
    void can_Not_updateAppointment_When_AppointmentNotExist() {
        // given
        long appointmentId = 0;
        AppointmentRequest appointmentRequest = new AppointmentRequest(LocalDateTime.of(2022, Month.FEBRUARY, 22, 18, 0),
                LocalDateTime.of(2022, Month.FEBRUARY, 22, 19, 0),"updated details", null);
        // when
        boolean expected = appointmentServiceUnderTest.updateAppointment(appointmentId, appointmentRequest);
        // then
        assertThat(expected).isFalse();
    }

    @Test
    @WithMockAppUser(email = "d@gmail.com",userRole = UserRole.DOCTOR)
    void can_ChangeAppointment() {
        // given
        AppUser patient = new AppUser(
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
        AppUser doctor = new AppUser(
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
        Appointment appointmentNumbOne = new Appointment(
                LocalDateTime.of(2022, Month.FEBRUARY, 20, 18, 0),
                LocalDateTime.of(2022, Month.FEBRUARY, 20, 19, 0),
                doctor,
                patient,
                "test details1",
                AppointmentStatus.ACCEPTED);
        Appointment appointmentNumbTwo = new Appointment(
                LocalDateTime.of(2022, Month.FEBRUARY, 22, 18, 0),
                LocalDateTime.of(2022, Month.FEBRUARY, 22, 19, 0),
                doctor,
                patient,
                "test details2",
                AppointmentStatus.FREE_DATE);
        long appointmentIdToCancel = appointmentNumbOne.getId();
        long freeDateAppointmentId = appointmentNumbTwo.getId();
        doReturn(true).when(appointmentServiceUnderTest).cancelAppointment(appointmentIdToCancel);
        doReturn(true).when(appointmentServiceUnderTest).signUpFreeDateAppointment(freeDateAppointmentId);
        // when
        boolean expected = appointmentServiceUnderTest.changeAppointment(appointmentIdToCancel, freeDateAppointmentId);
        // then
        assertThat(expected).isTrue();
    }

    @Test
    void can_Not_ChangeAppointment() {
        // given
        AppUser patient = new AppUser(
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
        AppUser doctor = new AppUser(
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
        Appointment appointmentNumbOne = new Appointment(
                LocalDateTime.of(2022, Month.FEBRUARY, 20, 18, 0),
                LocalDateTime.of(2022, Month.FEBRUARY, 20, 19, 0),
                doctor,
                patient,
                "test details1",
                AppointmentStatus.ACCEPTED);
        Appointment appointmentNumbTwo = new Appointment(
                LocalDateTime.of(2022, Month.FEBRUARY, 22, 18, 0),
                LocalDateTime.of(2022, Month.FEBRUARY, 22, 19, 0),
                doctor,
                patient,
                "test details2",
                AppointmentStatus.FREE_DATE);
        long appointmentIdToCancel = appointmentNumbOne.getId();
        long freeDateAppointmentId = appointmentNumbTwo.getId();
        doReturn(false).when(appointmentServiceUnderTest).cancelAppointment(appointmentIdToCancel);
        // when
        boolean expected = appointmentServiceUnderTest.changeAppointment(appointmentIdToCancel, freeDateAppointmentId);
        // then
        assertThat(expected).isFalse();

    }


}