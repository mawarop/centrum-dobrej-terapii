package com.example.centrum_dobrej_terapii.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalTime;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class AppUserDoctorWorkTimeResponse {
    @JsonFormat(pattern = "HH:mm:ss")
    LocalTime workHoursStart;
    @JsonFormat(pattern = "HH:mm:ss")
    LocalTime workHoursEnd;
}
