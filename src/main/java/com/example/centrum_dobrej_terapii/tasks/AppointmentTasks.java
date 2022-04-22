package com.example.centrum_dobrej_terapii.tasks;

import com.example.centrum_dobrej_terapii.AppointmentStatus;
import com.example.centrum_dobrej_terapii.entities.AppUser;
import com.example.centrum_dobrej_terapii.entities.Appointment;
import com.example.centrum_dobrej_terapii.repositories.AppointmentRepository;
import com.example.centrum_dobrej_terapii.services.EmailSender;
import com.example.centrum_dobrej_terapii.util.beans.EmailSenderHelper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@RequiredArgsConstructor
public class AppointmentTasks {
    private static final Logger LOG = LoggerFactory.getLogger(AppointmentTasks.class);
    private static final int MAIL_REMINDER_APPOINTMENTS_CHECK_TIME_PERIOD_IN_MINUTES = 110;
    public static final String APPOINTMENT_REMINDER_SUBJECT = "Przypomnienie o wizycie";

    private final AppointmentRepository appointmentRepository;
    private final EmailSender emailSender;
    private final EmailSenderHelper emailSenderHelper;


    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.DAYS)
    public void changeFinalizedAppointmentsStatus() {

        List<Appointment> appointments = appointmentRepository.findAll();
        appointments.forEach(appointment -> {
                    if (isAppointmentEndedOneDayAgo(appointment) && appointment.getAppointmentStatus().equals(AppointmentStatus.ACCEPTED))
                        appointment.setAppointmentStatus(AppointmentStatus.FINALIZED);
                }
        );
        appointmentRepository.saveAll(appointments);
        LOG.info("Finalized Appointments Status Updated");
    }

    @Scheduled(fixedRate = MAIL_REMINDER_APPOINTMENTS_CHECK_TIME_PERIOD_IN_MINUTES, timeUnit = TimeUnit.MINUTES)
    public void sendRemainderMailAtLeast24hBeforeVisitToPatient() {
        List<Appointment> acceptedAppointments = appointmentRepository.findAppointmentsByStatus(AppointmentStatus.ACCEPTED);
        AtomicBoolean remaindersSent = new AtomicBoolean(false);
        acceptedAppointments.forEach(appointment -> {
            if (isDateBetweenOrEqual(appointment.getStart(), LocalDateTime.now().plusHours(24), LocalDateTime.now().plusHours(26))) {
                AppUser patient = appointment.getPatient();
                String patientEmail = patient.getEmail();
                String patientHtmlContent = emailSenderHelper.buildAppointmentReminder24hEmailResponseContent(appointment);
                emailSender.send(patientEmail, APPOINTMENT_REMINDER_SUBJECT, patientHtmlContent);
                remaindersSent.set(true);
            }
        });
        if (remaindersSent.get()) {
            LOG.info("Email reminders sent");
        } else {
            LOG.info("Email reminders checked");
        }
    }

    private boolean isAppointmentEndedOneDayAgo(Appointment appointment) {
        return appointment.getEnd().isBefore(LocalDateTime.now().minusDays(1));
    }

    private boolean isDateBetweenOrEqual(LocalDateTime date, LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        return ((date.isAfter(rangeStart) && date.isBefore(rangeEnd)) || (date.isEqual(rangeStart) || date.isEqual(rangeEnd)));
    }
}
