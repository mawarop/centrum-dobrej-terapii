package com.example.centrum_dobrej_terapii.services;

import com.example.centrum_dobrej_terapii.entities.AppUser;
import com.example.centrum_dobrej_terapii.repositories.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserServiceImpl implements UserDetailsService,AppUserService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


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
}
