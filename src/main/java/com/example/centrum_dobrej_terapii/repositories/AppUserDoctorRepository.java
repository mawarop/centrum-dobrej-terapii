package com.example.centrum_dobrej_terapii.repositories;

import com.example.centrum_dobrej_terapii.dtos.AppUserDoctorDTO;
import com.example.centrum_dobrej_terapii.entities.AppUserDoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AppUserDoctorRepository extends JpaRepository<AppUserDoctor,Long> {
    @Query("Select new com.example.centrum_dobrej_terapii.dtos.AppUserDoctorDTO(aud.workHoursStart, aud.workHoursEnd) from AppUserDoctor aud  " +
            "where aud.doctor.email = :doctorEmail")
    Optional<AppUserDoctorDTO> findWorkHours(@Param("doctorEmail") String doctorEmail);
}
