package com.example.centrum_dobrej_terapii.controllers;

import com.example.centrum_dobrej_terapii.UserRole;
import com.example.centrum_dobrej_terapii.dtos.AppUserMapper;
import com.example.centrum_dobrej_terapii.dtos.AppUserRequest;
import com.example.centrum_dobrej_terapii.dtos.AppUserResponse;
import com.example.centrum_dobrej_terapii.entities.AppUser;
import com.example.centrum_dobrej_terapii.services.AppUserService;
import com.example.centrum_dobrej_terapii.services.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/admin")
@AllArgsConstructor

public class AdminController {
    private final AppointmentService appointmentService;
    private final AppUserService appUserService;
    private AppUserMapper appUserMapper;


    @GetMapping("users/{page}")
    public ResponseEntity<?> getUsers(@PathVariable("page") int page){
        List<AppUser> users =appUserService.getAllAppUsers(page);
        List<AppUserResponse> appUserResponses = appUserMapper.appUsersToAppUserResponses(users);
        long numberOfUsers = appUserService.getNumberOfUsers();
        long numberOfPages = appUserService.getNumberOfPages(numberOfUsers);
        Map<String, Object> response = new HashMap<>();
        response.put("users", appUserResponses);
        response.put("totalUsers", numberOfUsers);
        response.put("totalPages", numberOfPages);
        response.put("currentPage", page);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("user")
    public ResponseEntity createUser(@RequestParam(value = "userRoleParam",required = false) UserRole userRoleParam, @RequestBody AppUserRequest request){
        boolean succesfullCreatedUser = false;
        if(userRoleParam == null) {
            succesfullCreatedUser = appUserService.signUpUser(new AppUser(request, UserRole.PATIENT));
        }
        else {
            succesfullCreatedUser =  appUserService.signUpUser(new AppUser(request, userRoleParam));
        }

        if(succesfullCreatedUser){
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PatchMapping("user/{id}")
    public ResponseEntity updateUser(@PathVariable("id") int id, @RequestBody AppUserRequest appUserRequest){
        appUserService.updateAppUser(id, appUserRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("user/{id}/block")
    public ResponseEntity blockUser(@PathVariable("id") int id)
    {
        appUserService.blockAppUser(id);
        return new ResponseEntity(HttpStatus.OK);
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

