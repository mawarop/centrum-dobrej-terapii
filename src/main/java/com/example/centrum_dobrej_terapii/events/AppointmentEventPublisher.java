package com.example.centrum_dobrej_terapii.events;

import com.example.centrum_dobrej_terapii.entities.Appointment;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppointmentEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishAppointmentSignedUpEvent(Appointment appointment) {
        AppointmentSignedUpEvent appointmentSignedUpEvent = new AppointmentSignedUpEvent(this, appointment);
        applicationEventPublisher.publishEvent(appointmentSignedUpEvent);
    }

    public void publishAppointmentCanceledEvent(Appointment appointment) {
        AppointmentCanceledEvent appointmentCanceledEvent = new AppointmentCanceledEvent(this, appointment);
        applicationEventPublisher.publishEvent(appointmentCanceledEvent);
    }

}
