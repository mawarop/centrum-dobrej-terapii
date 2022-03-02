package com.example.centrum_dobrej_terapii.controllers;

import com.example.centrum_dobrej_terapii.AppointmentStatus;
import com.example.centrum_dobrej_terapii.dtos.AppUserDoctorBaseResponse;
import com.example.centrum_dobrej_terapii.dtos.AppointmentResponse;
import com.example.centrum_dobrej_terapii.entities.Appointment;
import com.example.centrum_dobrej_terapii.services.AppUserService;
import com.example.centrum_dobrej_terapii.services.AppointmentService;
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
        private final AppointmentService appointmentService;
        private final AppUserService appUserService;

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

    public List<AppointmentResponse> getAppointmentsResponseWithDoctorsNames()
    {
        List<Appointment> appointments = appointmentService.getParticipantAppointments();
        return appointments.stream().map(a -> new AppointmentResponse(a.getId(), a.getStart(), a.getEnd(), a.getDetails(),
                a.getAppointmentStatus(), a.getDoctor().getFirstname(), a.getDoctor().getLastname())).toList();
    }

    @PatchMapping("appointment/{id}")
    public ResponseEntity signUpFreeDateAppointment(@PathVariable("id") long id){
        boolean updated = appointmentService.signUpFreeDateAppointment(id);
        if(updated) {
            return new ResponseEntity(HttpStatus.OK);
        }
        else return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("doctors")
    public List<AppUserDoctorBaseResponse>getDoctorsBaseData(){
        return appUserService.getDoctorsBaseData();
    }

    @GetMapping("doctor-appointments")
    public List<AppointmentResponse> getDoctorAppointmentsResponse(@RequestParam String email)
    {
        List<Appointment> appointments = appointmentService.getUserAppointmentsByUserEmailAndAppointmentStatus(email, AppointmentStatus.FREE_DATE);
        return appointments.stream().map(a -> new AppointmentResponse(a.getId(), a.getStart(), a.getEnd(), a.getDetails(),
                a.getAppointmentStatus(), a.getDoctor().getFirstname(), a.getDoctor().getLastname())).toList();
    }

    @PatchMapping("cancel-appointment/{id}")
    ResponseEntity cancelAppointment(@PathVariable("id") long id){
        boolean canceledAppointment = appointmentService.cancelAppointment(id);
        if (canceledAppointment){
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("change-appointment")
    ResponseEntity changeAppointment(@RequestParam("appointmentIdToCancel") long appointmentIdToCancel,
                                     @RequestParam("freeDateAppointmentId") long freeDateAppointmentId) {
        if(appointmentService.changeAppointment(appointmentIdToCancel, freeDateAppointmentId))
            return new ResponseEntity(HttpStatus.OK);
        else return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
