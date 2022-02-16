package com.example.centrum_dobrej_terapii.dtos;

import com.example.centrum_dobrej_terapii.Specialization;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class AppUserDoctorBaseResponse extends AppUserBaseResponse {
    @Enumerated(EnumType.STRING)
    Specialization specialization;

    public AppUserDoctorBaseResponse(String firstname, String lastname, String email, Specialization specialization) {
        super(firstname, lastname, email);
        this.specialization = specialization;
    }
}
