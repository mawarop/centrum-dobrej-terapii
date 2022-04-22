package com.example.centrum_dobrej_terapii.events;

import com.example.centrum_dobrej_terapii.entities.AppUser;
import com.example.centrum_dobrej_terapii.entities.Appointment;
import com.example.centrum_dobrej_terapii.services.EmailSender;
import com.example.centrum_dobrej_terapii.util.beans.EmailSenderHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
@RequiredArgsConstructor
public class AppointmentEventListener {
    public static final String APPOINTMENT_SIGNED_UP_SUBJECT = "Nowa wizyta";
    private static final String APPOINTMENT_CANCELED_SUBJECT = "Anulowana wizyta";
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final EmailSender emailSender;
    private final EmailSenderHelper emailSenderHelper;

    @Async
    @EventListener
    public void onAppointmentSignedUpEvent(AppointmentSignedUpEvent appointmentSignedUpEvent) {
        Appointment appointment = appointmentSignedUpEvent.getAppointment();
        AppUser patient = appointment.getPatient();
        String patientEmail = patient.getEmail();
        String htmlContent = emailSenderHelper.buildAppointmentSignedUpEmailResponseContent(appointment);
        emailSender.send(patientEmail, APPOINTMENT_SIGNED_UP_SUBJECT, htmlContent);

    }

    @Async
    @EventListener
    public void onAppointmentCanceledEvent(AppointmentCanceledEvent appointmentCanceledEvent) {
        Appointment appointment = appointmentCanceledEvent.getAppointment();
        AppUser patient = appointment.getPatient();
        String patientEmail = patient.getEmail();
        String patientHtmlContent = emailSenderHelper.buildAppointmentCanceledEmailResponseContent(appointment, patient);
        emailSender.send(patientEmail, APPOINTMENT_CANCELED_SUBJECT, patientHtmlContent);

        AppUser doctor = appointment.getDoctor();
        String doctorEmail = doctor.getEmail();
        String doctorHtmlContent = emailSenderHelper.buildAppointmentCanceledEmailResponseContent(appointment, doctor);
        emailSender.send(doctorEmail, APPOINTMENT_CANCELED_SUBJECT, doctorHtmlContent);
    }

}
