package com.example.centrum_dobrej_terapii.services;

import com.example.centrum_dobrej_terapii.dtos.AppUserDoctorBaseResponse;
import com.example.centrum_dobrej_terapii.dtos.AppUserRequest;
import com.example.centrum_dobrej_terapii.entities.AppUser;

import java.util.List;
import java.util.Optional;

public interface AppUserService {
    boolean signUpUser(AppUser appUser);

    List<AppUserDoctorBaseResponse> getDoctorsBaseData();

    Optional<AppUser> getAppUser(String email);

    Optional<AppUser> getAppUser(long id);

    List<AppUser> getAllAppUsers(int page);

    void updateAppUser(int id, AppUserRequest appUserRequest);

    void blockAppUser(int id);

    long getNumberOfAllAppUsers();

    long getNumberOfAllAppUsersPages(long numberOfUsers);

    String confirmToken(String token);

    int enableAppUser(String email);

    List<AppUser> getAppUsersByInput(String input, int page);

    long getNumberOfAppUsersByInput(String input);

    long getNumberOfAppUsersPagesByInput(long numberOfAppUsersByInput);


//    Map<String, Long> getNumbersOfUsersAndPages();

}
