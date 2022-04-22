package com.example.centrum_dobrej_terapii.util;

import com.example.centrum_dobrej_terapii.UserRole;
import com.example.centrum_dobrej_terapii.entities.AppUser;

public final class AppointmentUtil {
    private AppointmentUtil() {
    }


    public static boolean isDoctor(AppUser user) {
        return user.getUserRole().name().equals(UserRole.DOCTOR.name());
    }

    // przed test
//    public static boolean isPatient(AppUser user) {
//        return user.getUserRole().name().equals(UserRole.PATIENT.name());
//    }
//
//    public static boolean isAdmin(AppUser user) {
//        return user.getUserRole().name().equals(UserRole.ADMIN.name());
//    }

    public static boolean isPatient(AppUser user) {
        return user.getUserRole().name().equals(UserRole.PATIENT.name());
    }

    public static boolean isAdmin(AppUser user) {
        return user.getUserRole().name().equals(UserRole.ADMIN.name());
    }

}
