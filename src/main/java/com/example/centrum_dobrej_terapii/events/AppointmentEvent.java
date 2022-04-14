package com.example.centrum_dobrej_terapii.events;

import org.springframework.context.ApplicationEvent;

public class AppointmentEvent extends ApplicationEvent {
    public AppointmentEvent(Object source) {
        super(source);
    }
}
