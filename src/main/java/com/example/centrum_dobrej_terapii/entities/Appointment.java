package com.example.centrum_dobrej_terapii.entities;

import com.example.centrum_dobrej_terapii.UserRole;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;

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
    private LocalDateTime start;
    @NotNull
    @NotEmpty
    private LocalDateTime end;
    private String details;
    @ManyToOne
    AppUser doctor;
    @ManyToOne
    AppUser patient;


    public Appointment(LocalDateTime start, LocalDateTime end, AppUser doctor, AppUser patient) {
        this.start = start;
        this.end = end;
        this.doctor = doctor;
        this.patient = patient;
    }

}


