package com.example.centrum_dobrej_terapii.controllers;

import com.example.centrum_dobrej_terapii.services.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Secured("ADMIN")
@RestController
@RequestMapping("api/admin")
@AllArgsConstructor
//@PreAuthorize("hasRole('ROLE_ADMIN')")

public class AdminController {
    private final AppointmentService appointmentService;

// przed refactoryzacja
//    @PostMapping("appointment/add-free-date")
//    public ResponseEntity addFreeDateAppointment(@RequestBody AppointmentRequest appointmentRequest) {
//        boolean addDateWithSuccess =adminService.addFreeDateAppointment(appointmentRequest);
//        if(addDateWithSuccess){
//            return new ResponseEntity(HttpStatus.CREATED);
//        }
//        else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//
//    }
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

