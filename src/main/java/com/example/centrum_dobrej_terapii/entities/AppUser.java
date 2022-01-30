package com.example.centrum_dobrej_terapii.entities;

import com.example.centrum_dobrej_terapii.UserRole;
import com.example.centrum_dobrej_terapii.dtos.RegistrationRequest;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class AppUser implements UserDetails {
    @Id
    @NotNull
    @NotEmpty
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String pesel;
    @NotNull
    @NotEmpty
    private String firstname;
    @NotNull
    @NotEmpty
    private String lastname;
    @NotNull
    @NotEmpty
    private String email;
    @NotNull
    @NotEmpty
    private String phone_number;
    @NotNull
    @NotEmpty
    @Enumerated(EnumType.STRING)
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

    public AppUser(RegistrationRequest request, UserRole role) {
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
