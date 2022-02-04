package com.example.centrum_dobrej_terapii.controllers;

import com.example.centrum_dobrej_terapii.dtos.AppointmentDTO;
import com.example.centrum_dobrej_terapii.services.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/patient")
//@PreAuthorize("hasRole('PATIENT')")
public class PatientController {
        PatientService patientService;

//    @PostMapping("/appointment/add")
//    public ResponseEntity addAppointment(@RequestBody AppointmentRequest appointmentRequest){
//        Optional<AppUser> doctor = appUserRepository.findByEmail(appointmentRequest.getSecond_participant());
//        AppUser principal = (AppUser) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
//        try {
//            if (doctor.isPresent() && doctor.get().getUserRole().name().equals(UserRole.DOCTOR.name())) {
//                Appointment appointment = new Appointment(appointmentRequest.getStart(), appointmentRequest.getEnd(), doctor.get(), principal,appointmentRequest.getDetails(), AppointmentStatus.ACCEPTED);
//                appointmentRepository.save(appointment);
//            } else {
//                throw new IllegalStateException("there is no doctor with this email");
//            }
//        }
//        catch (IllegalStateException illegalStateException){
//            System.out.println(illegalStateException.getMessage());
//            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        return new ResponseEntity(HttpStatus.CREATED);
//    }
//@PreAuthorize("hasRole('PATIENT')")
    @GetMapping("appointments")

    public List<AppointmentDTO> getAppointments()
    {
        return patientService.getAppointments();
    }

}
