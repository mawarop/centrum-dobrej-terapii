package com.example.centrum_dobrej_terapii.dtos;

import lombok.*;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
public class AppUserBaseResponse {

    private String firstname;
    private String lastname;
    private String email;
}
