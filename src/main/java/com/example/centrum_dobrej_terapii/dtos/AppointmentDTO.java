package com.example.centrum_dobrej_terapii.dtos;

import com.example.centrum_dobrej_terapii.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class AppointmentDTO {
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;
    private String details;
    private AppointmentStatus appointmentStatus;
    private String secondParticipantFirstname;
    private String secondParticipantLastname;

    public AppointmentDTO(LocalDateTime start, LocalDateTime end, String details, AppointmentStatus appointmentStatus) {
        this.start = start;
        this.end = end;
        this.details = details;
        this.appointmentStatus = appointmentStatus;
    }
}

