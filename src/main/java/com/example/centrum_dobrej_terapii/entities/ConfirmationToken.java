package com.example.centrum_dobrej_terapii.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdDateTime;
    @Column(nullable = false)
    private LocalDateTime expiresDateTime;

    private LocalDateTime confirmedDateTime;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    private AppUser appUser;

    public ConfirmationToken(String token, LocalDateTime createdDateTime, LocalDateTime expiresDateTime, AppUser appUser) {
        this.token = token;
        this.createdDateTime = createdDateTime;
        this.expiresDateTime = expiresDateTime;
        this.appUser = appUser;
    }
}
