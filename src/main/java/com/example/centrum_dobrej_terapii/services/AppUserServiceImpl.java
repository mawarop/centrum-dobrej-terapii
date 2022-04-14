package com.example.centrum_dobrej_terapii.services;

import com.example.centrum_dobrej_terapii.dtos.AppUserDoctorBaseResponse;
import com.example.centrum_dobrej_terapii.dtos.AppUserMapper;
import com.example.centrum_dobrej_terapii.dtos.AppUserRequest;
import com.example.centrum_dobrej_terapii.entities.AppUser;
import com.example.centrum_dobrej_terapii.entities.ConfirmationToken;
import com.example.centrum_dobrej_terapii.exceptions.AppUserNotFoundException;
import com.example.centrum_dobrej_terapii.repositories.AppUserDoctorRepository;
import com.example.centrum_dobrej_terapii.repositories.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserServiceImpl implements UserDetailsService, AppUserService {

    public static final int PAGE_SIZE = 5;
    private static final String APP_USER_NOT_FOUND_MESSAGE = "AppUser not found in database";
    public static final String EMAIL_SUBJECT = "Centrum dorej terapii - Potwierdź swój email";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AppUserDoctorRepository appUserDoctorRepository;
    private final AppUserMapper appUserMapper;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final TemplateEngine templateEngine;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format("User with email: %s not found", email)));
    }

    @Transactional
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
        } catch (IllegalStateException illegalStateException) {
            System.out.println(illegalStateException.getMessage());
            return false;
        }
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());


        appUser.setPassword(encodedPassword);
        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(20),
                appUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        final String LINK = "http://localhost:8080/api/registration/confirm?token=" + token;
        emailSender.send(appUser.getEmail(), EMAIL_SUBJECT,
                buildRegistrationResponseEmailContent(appUser.getFirstname() + " " + appUser.getLastname(), LINK));

        return true;
    }

    @Transactional()
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token).orElseThrow(() -> new IllegalStateException("token not found"));
        if (confirmationToken.getConfirmedDateTime() != null) {
            throw new IllegalStateException("email already confirmed");
        }
        LocalDateTime expiresDateTime = confirmationToken.getExpiresDateTime();
        if (expiresDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }
        confirmationTokenService.setConfirmedDateTime(token);
        this.enableAppUser(confirmationToken.getAppUser().getEmail());
        return "Email zostal potwierdzony";
    }

    @Override
    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
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

    @Override
    public Optional<AppUser> getAppUser(long id) {
        return appUserRepository.findById(id);
    }

    @Override
    public void updateAppUser(int id, AppUserRequest appUserRequest) {
        System.out.println("appuserRequest: " + appUserRequest);
        Optional<AppUser> appUserOptional = this.getAppUser(id);
        if (appUserOptional.isPresent()) {
            AppUser user = appUserOptional.get();
            appUserMapper.updateAppUserFromAppUserRequest(appUserRequest, user);
            System.out.println("userUpdated: " + user);
            appUserRepository.save(user);
        } else throw new IllegalStateException("User not found");
    }

    @Override
    public void blockAppUser(int id) {
        Optional<AppUser> appUserOptional = this.getAppUser(id);
        if (appUserOptional.isPresent()) {
            AppUser user = appUserOptional.get();
            user.setLocked(true);
            appUserRepository.save(user);
        } else throw new IllegalStateException("User not found");
    }

    @Override
    public long getNumberOfAllAppUsers() {
        return appUserRepository.count();
    }

    @Override
    public long getNumberOfAllAppUsersPages(long numberOfUsers) {
        return (long) Math.ceil((double) numberOfUsers / (double) PAGE_SIZE);
    }


    private String buildRegistrationResponseEmailContent(String name, String link) {
//        return "<p>Witaj " + name + "! Dziękujemy za wybranie naszej placówki</p>" +
//                "<p>Aby dokończyć proces rejestracji proszę kliknąć poniższy link</p>" +
//                "<p><a href=\"" + link + "\">" + "Aktywuj" + "</a></p>" +
//                "<p>Link wygaśnie w ciągu 20 minut</p>";
        final Context ctx = new Context();
        ctx.setVariable("name", name);
        ctx.setVariable("link", link);
        return templateEngine.process("html/user_registration_email_resp.html", ctx);
    }

    @Override
    public List<AppUser> getAppUsersByInput(String input, int page) {
        List<String> splited;
        List<AppUser> appUsers;
        if (input.contains(" ")) {
            splited = Arrays.stream(input.split("\\s+")).toList();
            String nameNumbOne = splited.get(0);
            String nameNumbTwo = splited.get(1);
            appUsers = appUserRepository.findAppUsersByInput(nameNumbOne, nameNumbTwo, PageRequest.of(page, PAGE_SIZE));
        } else {
            appUsers = appUserRepository.findAppUsersByInput(
                    input, PageRequest.of(page, PAGE_SIZE));
        }

        if (appUsers.isEmpty())
            throw new AppUserNotFoundException(APP_USER_NOT_FOUND_MESSAGE);
        return appUsers;
    }

    @Override
    public long getNumberOfAppUsersByInput(String input) {
        List<String> splited;
        long numbOfAppUsers;
        if (input.contains(" ")) {
            splited = Arrays.stream(input.split("\\s+")).toList();
            String nameNumbOne = splited.get(0);
            String nameNumbTwo = splited.get(1);
            numbOfAppUsers = appUserRepository.countAppUsersByInput(nameNumbOne, nameNumbTwo);
        } else {
            numbOfAppUsers = appUserRepository.countAppUsersByInput(input);
        }

        if (numbOfAppUsers == 0)
            throw new AppUserNotFoundException(APP_USER_NOT_FOUND_MESSAGE);
        return numbOfAppUsers;
    }

    @Override
    public long getNumberOfAppUsersPagesByInput(long numberOfAppUsersByInput) {
        return (long) Math.ceil((double) numberOfAppUsersByInput / (double) PAGE_SIZE);

    }
}
