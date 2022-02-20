package com.example.centrum_dobrej_terapii.services;

import com.example.centrum_dobrej_terapii.dtos.AppUserDoctorBaseResponse;
import com.example.centrum_dobrej_terapii.entities.AppUser;
import com.example.centrum_dobrej_terapii.repositories.AppUserDoctorRepository;
import com.example.centrum_dobrej_terapii.repositories.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserServiceImpl implements UserDetailsService,AppUserService {

    public static final int PAGE_SIZE = 5;
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AppUserDoctorRepository appUserDoctorRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format("User with email: %s not found", email)));
    }
        public boolean signUpUser(AppUser appUser) {
        boolean userWithEmailExists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();
        boolean userWithPeselExists = appUserRepository.findByPesel(appUser.getPesel()).isPresent();
        boolean userWithPhoneNumberExists = appUserRepository.findByPhone_number((appUser.getPhone_number())).isPresent();

        try {
            if (userWithEmailExists) {
                throw new IllegalStateException("Email already taken");
            }
            if (userWithPeselExists) {
                throw new IllegalStateException("Pesel already taken");
            }
            if (userWithPhoneNumberExists) {
                throw new IllegalStateException("Phone number already taken");
            }
        }catch (IllegalStateException illegalStateException){
            System.out.println(illegalStateException.getMessage());
            return false;
        }
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());


        appUser.setPassword(encodedPassword);
        appUserRepository.save(appUser);

            return true;
        }

    @Override
    public List<AppUserDoctorBaseResponse> getDoctorsBaseData() {
        List<AppUserDoctorBaseResponse> appUserDoctorBaseResponseList = appUserDoctorRepository.findAppUserDoctorBaseData();
        return appUserDoctorBaseResponseList;
    }

    @Override
    public Optional<AppUser> getAppUser(String email) {
        return appUserRepository.findByEmail(email);
    }

    @Override
    public List<AppUser> getAllAppUsers(int page) {
        return appUserRepository.findAllAppUsers(PageRequest.of(page, PAGE_SIZE));
    }
}
