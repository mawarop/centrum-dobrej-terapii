package com.example.centrum_dobrej_terapii.entities;

import com.example.centrum_dobrej_terapii.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Appointment {
    @Id
    @NotNull
    @NotEmpty
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @NotEmpty
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start;
    @NotNull
    @NotEmpty
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;
    private String details;
    @ManyToOne
    AppUser doctor;
    @ManyToOne(optional = true)
            //, fetch = FetchType.LAZY
    AppUser patient;
    @Enumerated(EnumType.STRING)
    AppointmentStatus appointmentStatus;

    public Appointment(LocalDateTime start, LocalDateTime end, AppUser doctor, AppUser patient, String details, AppointmentStatus appointmentStatus) {
        this.start = start;
        this.end = end;
        this.doctor = doctor;
        this.patient = patient;
        this.details = details;
        this.appointmentStatus = appointmentStatus;
    }

    public Appointment(LocalDateTime start, LocalDateTime end, AppUser doctor,String details, AppointmentStatus appointmentStatus) {
        this.start = start;
        this.end = end;
        this.doctor = doctor;
        this.details = details;
        this.appointmentStatus = appointmentStatus;
    }
}


