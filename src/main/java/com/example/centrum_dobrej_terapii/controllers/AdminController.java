package com.example.centrum_dobrej_terapii.controllers;

import com.example.centrum_dobrej_terapii.AppointmentStatus;
import com.example.centrum_dobrej_terapii.UserRole;
import com.example.centrum_dobrej_terapii.dtos.AppUserMapper;
import com.example.centrum_dobrej_terapii.dtos.AppUserRequest;
import com.example.centrum_dobrej_terapii.dtos.AppUserResponse;
import com.example.centrum_dobrej_terapii.dtos.AppointmentRequestWithParticipants;
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
import java.util.Optional;

@RestController
@RequestMapping("api/admin")
@AllArgsConstructor

public class AdminController {
    private final AppointmentService appointmentService;
    private final AppUserService appUserService;
    private AppUserMapper appUserMapper;


//    @GetMapping("users/{page}")
//    public ResponseEntity<?> getUsers(@PathVariable("page") int page) {
//        List<AppUser> users = appUserService.getAllAppUsers(page);
//        List<AppUserResponse> appUserResponses = appUserMapper.appUsersToAppUserResponses(users);
//        long numberOfUsers = appUserService.getNumberOfUsers();
//        long numberOfPages = appUserService.getNumberOfPages(numberOfUsers);
//        Map<String, Object> response = new HashMap<>();
//        response.put("users", appUserResponses);
//        response.put("totalUsers", numberOfUsers);
//        response.put("totalPages", numberOfPages);
//        response.put("currentPage", page);
//        return new ResponseEntity(response, HttpStatus.OK);
//    }


    @GetMapping("users")
    public ResponseEntity<?> getUsers
            (@RequestParam int page, @RequestParam Optional<String> input) {

        List<AppUser> appUsers;
        long numberOfAppUsers;
        long numberOfAppUsersPages;
        if (input.isPresent()) {
            appUsers = appUserService.getAppUsersByInput(input.get(), page);
            numberOfAppUsers = appUserService.getNumberOfAppUsersByInput(input.get());
            numberOfAppUsersPages = appUserService.getNumberOfAppUsersPagesByInput(numberOfAppUsers);
        } else {
            appUsers = appUserService.getAllAppUsers(page);
            numberOfAppUsers = appUserService.getNumberOfAllAppUsers();
            numberOfAppUsersPages = appUserService.getNumberOfAllAppUsersPages(numberOfAppUsers);
        }

        List<AppUserResponse> appUserResponses = appUserMapper.appUsersToAppUserResponses(appUsers);

        Map<String, Object> response = new HashMap<>();
        response.put("users", appUserResponses);
        response.put("totalUsers", numberOfAppUsers);
        response.put("totalPages", numberOfAppUsersPages);
        response.put("currentPage", page);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("users")
    public ResponseEntity createUser(@RequestParam(value = "userRoleParam", required = false) UserRole userRoleParam, @RequestBody AppUserRequest request) {
        boolean succesfullCreatedUser = false;
        if (userRoleParam == null) {
            succesfullCreatedUser = appUserService.signUpUser(new AppUser(request, UserRole.PATIENT));
        } else {
            succesfullCreatedUser = appUserService.signUpUser(new AppUser(request, userRoleParam));
        }

        if (succesfullCreatedUser) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PatchMapping("users/{id}")
    public ResponseEntity updateUser(@PathVariable("id") int id, @RequestBody AppUserRequest appUserRequest) {
        appUserService.updateAppUser(id, appUserRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("users/{id}/block")
    public ResponseEntity blockUser(@PathVariable("id") int id) {
        appUserService.blockAppUser(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("appointment/add")
    public ResponseEntity addAppointment(@RequestBody AppointmentRequestWithParticipants appointmentRequest) {
        boolean appointmentAdditionSuccessfull = appointmentService.addAppointment(appointmentRequest, AppointmentStatus.FREE_DATE);
        if (appointmentAdditionSuccessfull)
            return new ResponseEntity(HttpStatus.CREATED);
        else
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}



