package com.example.centrum_dobrej_terapii.entities;

import com.example.centrum_dobrej_terapii.UserRole;
import com.example.centrum_dobrej_terapii.dtos.AppUserRequest;
import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.pl.PESEL;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Username nie może być pusty")
    @Size(min = 3)
    private String username;

    @NotBlank(message = "Hasło nie może być puste")
//    @Size(min=8)
    private String password;

    @NotBlank(message = "Pesel nie może być pusty")
    @PESEL(message = "Niepoprawny pesel")
    @Column(unique = true)
    private String pesel;

    @Size(min = 3)
    @NotBlank(message = "Firstname nie może być puste")
    private String firstname;

    @Size(min = 3)
    @NotBlank(message = "Lastname nie może być puste")
    private String lastname;

    @NotBlank
    @Email(message = "Email powinien być poprawny")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Numer telefonu nie może być pusty")
    @Size(min = 9, max=9)
    @Pattern(regexp = "\\d{9}", message = "Niepoprawny format numeru telefonu")
    @Column(unique = true)
    private String phone_number;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserRole userRole;


    private Boolean locked = false;
    private Boolean enabled = false;



    public AppUser(String username, String password, String PESEL, String firstname, String lastname, String email, String phone_number, UserRole userRole, Boolean locked, Boolean enabled, Appointment appointment) {
        this.username = username;
        this.password = password;
        this.pesel = PESEL;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone_number = phone_number;
        this.userRole = userRole;
        this.locked = locked;
        this.enabled = enabled;
    }

    public AppUser(AppUserRequest request, UserRole role) {
        this.username = request.getUsername();
        this.password = request.getPassword();
        this.pesel = request.getPesel();
        this.firstname = request.getFirstname();
        this.lastname = request.getLastname();
        this.email = request.getEmail();
        this.phone_number = request.getPhone_number();
        this.userRole = role;
        this.locked = locked;
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());

        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
