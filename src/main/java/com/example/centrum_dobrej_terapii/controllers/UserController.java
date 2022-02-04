package com.example.centrum_dobrej_terapii.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor

public class UserController {


//    @GetMapping()
//    UserResponse getUser(){
//
//    }
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @ResponseBody
    @GetMapping
    String testU(){
        return "xddd";
    }



}
