package com.example.centrum_dobrej_terapii.configs;

import com.example.centrum_dobrej_terapii.entities.Appointment;
import com.example.centrum_dobrej_terapii.repositories.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.joda.time.Interval;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

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

    public boolean isAppointmentCancellationPossible(Optional<Appointment> appointment){
        final long DAY_IN_MINUTES = 1440;
        try {
            if(appointment.isPresent()) {
                LocalDateTime now = LocalDateTime.now();
                Duration duration = Duration.between(now, appointment.get().getStart());
                long difference = Math.abs(duration.toMinutes());
//            System.out.println("Roznica: " + difference);
                if (difference < DAY_IN_MINUTES || now.isAfter(appointment.get().getStart())) {
                    throw new IllegalStateException("Wizytę można anulować najpóźniej dzień przed");
                }
            }
            else throw new IllegalStateException("Nie znaleziono wizyty");
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return false;
        }
        return true;
    }
}
