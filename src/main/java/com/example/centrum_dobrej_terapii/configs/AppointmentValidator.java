package com.example.centrum_dobrej_terapii.configs;

import com.example.centrum_dobrej_terapii.entities.Appointment;
import com.example.centrum_dobrej_terapii.repositories.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.joda.time.Interval;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
@AllArgsConstructor
public class AppointmentValidator {
    private final AppointmentRepository appointmentRepository;
    public boolean appointmentOverlapsDateInDatabase(Appointment appointment){

        LocalDateTime appStart = appointment.getStart();
        LocalDateTime appEnd = appointment.getEnd();
        List<Appointment> appointments = appointmentRepository.findAll();
        Interval newAppointmentInterval = new Interval(appointment.getStart().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),(appointment.getEnd().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        for (Appointment a:
                appointments) {
            Interval aInterval = new Interval(a.getStart().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),(a.getEnd().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            if(aInterval.overlaps(newAppointmentInterval)){
                return true;
            }
        }
        return false;
    }
}
