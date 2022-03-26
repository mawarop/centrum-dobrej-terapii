package com.example.centrum_dobrej_terapii.repositories;

import com.example.centrum_dobrej_terapii.dtos.AppUserDoctorBaseResponse;
import com.example.centrum_dobrej_terapii.entities.AppUserDoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppUserDoctorRepository extends JpaRepository<AppUserDoctor, Long> {
//    @Query("Select new com.example.centrum_dobrej_terapii.dtos.AppUserDoctorWorkTimeResponse(aud.workHoursStart, aud.workHoursEnd) from AppUserDoctor aud  " +
//            "where aud.doctor.email = :doctorEmail")
//    Optional<AppUserDoctorWorkTimeResponse> findWorkHours(@Param("doctorEmail") String doctorEmail);

    @Query("select new com.example.centrum_dobrej_terapii.dtos.AppUserDoctorBaseResponse(aud.doctor.firstname, aud.doctor.lastname, aud.doctor.email, aud.specialization) from AppUserDoctor aud")
    List<AppUserDoctorBaseResponse> findAppUserDoctorBaseData();
//    @Query("SELECT aud.specialization from AppUserDoctor aud where aud.doctor.email = :email")
//    Optional<String> findSpecializationByDoctorEmail(@Param("email") String email);
}
