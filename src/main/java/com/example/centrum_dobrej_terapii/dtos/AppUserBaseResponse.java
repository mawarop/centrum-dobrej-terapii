package com.example.centrum_dobrej_terapii.dtos;

import lombok.*;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class AppUserBaseResponse {
    private String firstname;
    private String lastname;
//    private String pesel;
    private String email;
}
