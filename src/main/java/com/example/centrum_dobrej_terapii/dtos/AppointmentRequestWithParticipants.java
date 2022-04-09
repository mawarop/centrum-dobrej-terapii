package com.example.centrum_dobrej_terapii.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class AppointmentRequestWithParticipants {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime start;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime end;
//    private AppointmentStatus status;
    private String patientEmail = null;
    private String doctorEmail;
}
