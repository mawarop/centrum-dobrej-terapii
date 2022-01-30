package com.example.centrum_dobrej_terapii.dtos;

import com.example.centrum_dobrej_terapii.UserRole;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class UserResponse {

    long id;
    private String username;
    private String password;
    private String pesel;
    private String firstname;
    private String lastname;
    private String email;
    private String phone_number;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
