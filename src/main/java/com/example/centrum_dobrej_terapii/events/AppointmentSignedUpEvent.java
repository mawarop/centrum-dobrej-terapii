package com.example.centrum_dobrej_terapii.events;

import com.example.centrum_dobrej_terapii.entities.Appointment;
import lombok.Getter;

public class AppointmentSignedUpEvent extends AppointmentEvent {
    @Getter
    private final Appointment appointment;

    public AppointmentSignedUpEvent(Object source, Appointment appointment) {
        super(source);
        this.appointment = appointment;
    }
}
