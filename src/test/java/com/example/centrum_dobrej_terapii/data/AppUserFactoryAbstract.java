package com.example.centrum_dobrej_terapii.data;

import com.example.centrum_dobrej_terapii.UserRole;
import com.example.centrum_dobrej_terapii.entities.AppUser;

public class AppUserFactoryAbstract implements AbstractTestDataFactory<AppUser, UserRole> {
    @Override
    public AppUser create(UserRole type) {
        switch (type){
            case PATIENT -> {
                return new AppUser(
                            "abbbbpkowal",
                            "qwerty",
                            "77121727497",
                            "Piotr",
                            "Kowalski",
                            "patient@gmail.com",
                            "736346373",
                            UserRole.PATIENT,
                            false,
                            false);
            }
            case DOCTOR -> {
                return new AppUser(
                    "dnowak",
                    "qwerty123",
                    "60120215391",
                    "Sebastian",
                    "Nowak",
                    "doctor@gmail.com",
                    "745745684",
                    UserRole.DOCTOR,
                    false,
                    false);
            }
            case ADMIN -> {
                return new AppUser(
                        "anowak",
                        "abc",
                        "92080923876",
                        "Paweł",
                        "Nowak",
                        "admin@gmail.com",
                        "445745684",
                        UserRole.ADMIN,
                        false,
                        false);
            }
            default -> throw new IllegalStateException("Given role not implemented");

        }
    }
}
