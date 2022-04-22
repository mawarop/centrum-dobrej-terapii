package com.example.centrum_dobrej_terapii.util.beans;

import com.example.centrum_dobrej_terapii.entities.AppUser;
import com.example.centrum_dobrej_terapii.entities.Appointment;
import com.example.centrum_dobrej_terapii.events.AppointmentEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class EmailSenderHelper {

    private final TemplateEngine templateEngine;

    public String buildAppointmentCanceledEmailResponseContent(Appointment appointment, AppUser participant) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AppointmentEventListener.DEFAULT_DATETIME_FORMAT);
        String appointmentStart = appointment.getStart().format(formatter);

        final Context ctx = new Context();
        ctx.setVariable("firstName", participant.getFirstname());
        ctx.setVariable("lastName", participant.getLastname());
        ctx.setVariable("appointmentStart", appointmentStart);
        return templateEngine.process("html/patient/email/canceled_appointment_response.html", ctx);
    }

    public String buildAppointmentSignedUpEmailResponseContent(Appointment appointment) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AppointmentEventListener.DEFAULT_DATETIME_FORMAT);
        String appointmentStart = appointment.getStart().format(formatter);

        final Context ctx = new Context();
        ctx.setVariable("firstName", appointment.getPatient().getFirstname());
        ctx.setVariable("lastName", appointment.getPatient().getLastname());
        ctx.setVariable("appointmentStart", appointmentStart);
        return templateEngine.process("html/patient/email/signed_up_appointment_response.html", ctx);
    }

    public String buildAppointmentReminder24hEmailResponseContent(Appointment appointment) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AppointmentEventListener.DEFAULT_DATETIME_FORMAT);
        String appointmentStart = appointment.getStart().format(formatter);

        final Context ctx = new Context();
        ctx.setVariable("firstName", appointment.getPatient().getFirstname());
        ctx.setVariable("lastName", appointment.getPatient().getLastname());
        ctx.setVariable("appointmentStart", appointmentStart);
        return templateEngine.process("html/patient/email/appointment_reminder_response.html", ctx);
    }
}
