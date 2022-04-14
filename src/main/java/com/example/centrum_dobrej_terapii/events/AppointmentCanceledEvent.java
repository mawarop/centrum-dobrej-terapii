package com.example.centrum_dobrej_terapii.events;

import com.example.centrum_dobrej_terapii.entities.Appointment;
import lombok.Getter;

public class AppointmentCanceledEvent extends AppointmentEvent {
    @Getter
    private final Appointment appointment;

    public AppointmentCanceledEvent(Object source, Appointment appointment) {
        super(source);
        this.appointment = appointment;
    }
}
