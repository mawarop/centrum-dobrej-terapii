package com.example.centrum_dobrej_terapii.events;

import com.example.centrum_dobrej_terapii.entities.AppUser;
import com.example.centrum_dobrej_terapii.entities.Appointment;
import com.example.centrum_dobrej_terapii.services.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;

@Component
@EnableAsync
@RequiredArgsConstructor
public class AppointmentEventListener {
    public static final String APPOINTMENT_SIGNED_UP_SUBJECT = "Nowa wizyta";
    private static final String APPOINTMENT_CANCELED_SUBJECT = "Anulowana wizyta";
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final EmailSender emailSender;
    private final TemplateEngine templateEngine;

    @Async
    @EventListener
    public void onAppointmentSignedUpEvent(AppointmentSignedUpEvent appointmentSignedUpEvent) {
        Appointment appointment = appointmentSignedUpEvent.getAppointment();
        AppUser patient = appointment.getPatient();
        String patientEmail = patient.getEmail();
        String htmlContent = buildAppointmentSignedUpEmailResponseContent(appointment);
        emailSender.send(patientEmail, APPOINTMENT_SIGNED_UP_SUBJECT, htmlContent);

    }

    @Async
    @EventListener
    public void onAppointmentCanceledEvent(AppointmentCanceledEvent appointmentCanceledEvent) {
        Appointment appointment = appointmentCanceledEvent.getAppointment();
        AppUser patient = appointment.getPatient();
        String patientEmail = patient.getEmail();
        String htmlContent = buildAppointmentCanceledEmailResponseContent(appointment);
        emailSender.send(patientEmail, APPOINTMENT_CANCELED_SUBJECT, htmlContent);
    }

    private String buildAppointmentCanceledEmailResponseContent(Appointment appointment) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT);
        String appointmentStart = appointment.getStart().format(formatter);

        final Context ctx = new Context();
        ctx.setVariable("firstName", appointment.getPatient().getFirstname());
        ctx.setVariable("lastName", appointment.getPatient().getLastname());
        ctx.setVariable("appointmentStart", appointmentStart);
        return templateEngine.process("html/patient/email/canceled_appointment_response.html", ctx);
    }

    private String buildAppointmentSignedUpEmailResponseContent(Appointment appointment) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT);
        String appointmentStart = appointment.getStart().format(formatter);

        final Context ctx = new Context();
        ctx.setVariable("firstName", appointment.getPatient().getFirstname());
        ctx.setVariable("lastName", appointment.getPatient().getLastname());
        ctx.setVariable("appointmentStart", appointmentStart);
        return templateEngine.process("html/patient/email/signed_up_appointment_response.html", ctx);
    }

}
