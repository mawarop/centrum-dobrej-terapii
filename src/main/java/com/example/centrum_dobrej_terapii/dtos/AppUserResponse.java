package com.example.centrum_dobrej_terapii.dtos;

import com.example.centrum_dobrej_terapii.UserRole;
import lombok.*;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
public class AppUserResponse extends AppUserBaseResponse{

//    private long id;
    private String username;
    private String phone_number;
    private UserRole userRole;

    public AppUserResponse(String firstname, String lastname, String email, String username, String phone_number, UserRole userRole) {
        super(firstname, lastname, email);
        this.username = username;
        this.phone_number = phone_number;
        this.userRole = userRole;
    }
}
