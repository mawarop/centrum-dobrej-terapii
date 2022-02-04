package com.example.centrum_dobrej_terapii.entities;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class AppUserDoctor {
    @Id
    @NotNull
    @NotEmpty
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    AppUser doctor;
    LocalTime workHoursStart;
    LocalTime workHoursEnd;

}
