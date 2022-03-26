package com.example.centrum_dobrej_terapii.repositories;

import com.example.centrum_dobrej_terapii.entities.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Transactional
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByToken(String token);

    @Modifying
    @Query("update ConfirmationToken c " +
            "set c.confirmedDateTime = ?2 " +
            "where c.token = ?1")
    int updateConfirmedDate(String token, LocalDateTime confirmedDateTime);
}
