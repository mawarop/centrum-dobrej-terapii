package com.example.centrum_dobrej_terapii.dtos;

import lombok.*;



@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class RegistrationRequest {

    private String username;
    private String password;
    private String pesel;
    private String firstname;
    private String lastname;
    private String email;
    private String phone_number;
}
