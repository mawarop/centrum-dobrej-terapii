package com.example.centrum_dobrej_terapii.dtos;

import com.example.centrum_dobrej_terapii.AppointmentStatus;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class AppointmentResponse extends AppointmentBaseResponse{

    private String secondParticipantFirstname;
    private String secondParticipantLastname;

    public AppointmentResponse(long id, LocalDateTime start, LocalDateTime end, String details, AppointmentStatus appointmentStatus, String secondParticipantFirstname, String secondParticipantLastname) {
        super(id, start, end, details, appointmentStatus);
        this.secondParticipantFirstname = secondParticipantFirstname;
        this.secondParticipantLastname = secondParticipantLastname;
    }
    //    public AppointmentResponse(LocalDateTime start, LocalDateTime end, String details, AppointmentStatus appointmentStatus) {
//        this.start = start;
//        this.end = end;
//        this.details = details;
//        this.appointmentStatus = appointmentStatus;
//    }
//
//    public AppointmentResponse(long id, LocalDateTime start, LocalDateTime end, String details, AppointmentStatus appointmentStatus) {
//        this.id = id;
//        this.start = start;
//        this.end = end;
//        this.details = details;
//        this.appointmentStatus = appointmentStatus;
//    }
}

