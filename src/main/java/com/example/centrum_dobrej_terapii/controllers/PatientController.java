package com.example.centrum_dobrej_terapii.controllers;

import com.example.centrum_dobrej_terapii.AppointmentStatus;
import com.example.centrum_dobrej_terapii.dtos.AppUserDoctorBaseResponse;
import com.example.centrum_dobrej_terapii.dtos.AppointmentResponse;
import com.example.centrum_dobrej_terapii.services.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    public List<AppointmentResponse> getAppointments()
    {
        return patientService.getAppointments();
    }

    @PatchMapping("appointment/{id}")
    public ResponseEntity signUpFreeDateAppointment(@PathVariable("id") long id){
        boolean updated = patientService.signUpFreeDateAppointment(id);
        if(updated) {
            return new ResponseEntity(HttpStatus.OK);
        }
        else return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("doctors")
    public List<AppUserDoctorBaseResponse>getDoctorsBaseData(){
        return patientService.getDoctorsBaseData();
    }

    @GetMapping("doctor-appointments")
    public List<AppointmentResponse> getDoctorAppointmentsByAppointmentStatus(@RequestParam String email)
    {
        return patientService.getDoctorAppointmentsByAppointmentStatus(email, AppointmentStatus.FREE_DATE);
    }

    @PatchMapping("cancel-appointment/{id}")
    ResponseEntity cancelAppointment(@PathVariable("id") long id){
        boolean canceledAppointment = patientService.cancelAppointment(id);
        if (canceledAppointment){
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
