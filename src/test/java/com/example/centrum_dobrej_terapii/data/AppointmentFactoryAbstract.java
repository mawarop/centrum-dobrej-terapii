package com.example.centrum_dobrej_terapii.data;

import com.example.centrum_dobrej_terapii.AppointmentStatus;
import com.example.centrum_dobrej_terapii.entities.Appointment;

import java.time.LocalDateTime;
import java.time.Month;

public class AppointmentFactoryAbstract implements AbstractTestDataFactory<Appointment, String> {
    @Override
    public Appointment create(String num) {
        switch (num){
            case "numberOne" -> {
                return new Appointment(LocalDateTime.of(2022, Month.DECEMBER, 20, 18, 0),
                        LocalDateTime.of(2022, Month.DECEMBER, 20, 19, 0),
                        null, null, "test details1", AppointmentStatus.ACCEPTED);
            }
            case "numberTwo" -> {
                return new Appointment(LocalDateTime.of(2022, Month.DECEMBER, 20, 20, 0),
                        LocalDateTime.of(2022, Month.DECEMBER, 20, 21, 0),
                        null, null, "test details2", AppointmentStatus.ACCEPTED);
            }
            default -> throw new IllegalStateException("Cant create appointment");
            }
        }
    }
