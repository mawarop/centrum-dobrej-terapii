package com.example.centrum_dobrej_terapii.controllers;

import com.example.centrum_dobrej_terapii.MapStructMapper;
import com.example.centrum_dobrej_terapii.dtos.AppUserResponse;
import com.example.centrum_dobrej_terapii.entities.AppUser;
import com.example.centrum_dobrej_terapii.services.AppUserService;
import com.example.centrum_dobrej_terapii.services.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@Secured("ADMIN")
@RestController
@RequestMapping("api/admin")
@AllArgsConstructor
//@PreAuthorize("hasRole('ROLE_ADMIN')")

public class AdminController {
    private final AppointmentService appointmentService;
    private final AppUserService appUserService;
    private MapStructMapper mapStructMapper;


    @GetMapping("users/{page}")
    public List<AppUserResponse> getUsers(@PathVariable("page") int page){
        List<AppUser> users =appUserService.getAllAppUsers(page);
        return mapStructMapper.appUsersToAppUserResponses(users);
    }





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

