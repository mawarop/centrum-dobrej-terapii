package com.example.centrum_dobrej_terapii.dtos;

import com.example.centrum_dobrej_terapii.entities.AppUser;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class AppointmentRequest {
    private LocalDateTime start;
    private LocalDateTime end;
    private String details;
    private String doctor_email;
}
