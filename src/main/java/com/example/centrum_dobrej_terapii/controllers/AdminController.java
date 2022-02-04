package com.example.centrum_dobrej_terapii.controllers;

import com.example.centrum_dobrej_terapii.dtos.AppointmentRequest;
import com.example.centrum_dobrej_terapii.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Secured("ADMIN")
@RestController
@RequestMapping("api/admin")
@AllArgsConstructor
//@PreAuthorize("hasRole('ROLE_ADMIN')")

public class AdminController {
    AdminService adminService;


    @PostMapping("appointment/add-free-date")
    public ResponseEntity addFreeDateAppointment(@RequestBody AppointmentRequest appointmentRequest) {
        return adminService.addFreeDateAppointment(appointmentRequest);

    }
}
//    @PostMapping("/add-doctor-appointments")
//    ResponseEntity<HttpStatus> addDoctorAppointments(@RequestBody String email, int days)
//    {
//
//        Optional<AppUserDoctorDTO> appUserDoctorDTO= appUserDoctorRepository.findWorkHours(email);
//        List<AppointmentDTO> appointmentDTOS = appointmentRepository.findByDoctorEmail(email);
//        if(appUserDoctorDTO.isPresent()){
//            LocalTime workHoursStart = appUserDoctorDTO.get().getWorkHoursStart();
//            LocalTime workHoursEnd = appUserDoctorDTO.get().getWorkHoursEnd();
//            LocalDateTime dateTime = LocalDateTime.now();
//            List<LocalDateTime> occupatedDateTimeIntervals = new ArrayList<LocalDateTime>();
//            while(Duration.between(dateTime, LocalDateTime.now()).toDays() <= days){
//                for (AppointmentDTO appointment: appointmentDTOS) {
//
//                }
//
//
//                dateTime = dateTime.plusDays(1);
//
//            }
//
//
//        }else{
//            throw new IllegalStateException("Doktor z o podanym emailu nie istnieje");
//        }
//        return new ResponseEntity(HttpStatus.CREATED);
//    }

