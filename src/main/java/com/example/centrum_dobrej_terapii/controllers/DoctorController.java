package com.example.centrum_dobrej_terapii.controllers;


import com.example.centrum_dobrej_terapii.dtos.AppointmentRequest;
import com.example.centrum_dobrej_terapii.services.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/doctor")
@AllArgsConstructor
//@PreAuthorize("hasRole('DOCTOR')")
public class DoctorController {
private final DoctorService doctorService;

    @PostMapping("appointment/add")
    public ResponseEntity addAppointment(@RequestBody AppointmentRequest appointmentRequest){
        return doctorService.addAppointment(appointmentRequest);
    }
}
