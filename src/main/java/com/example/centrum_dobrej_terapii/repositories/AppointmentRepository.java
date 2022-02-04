package com.example.centrum_dobrej_terapii.repositories;

import com.example.centrum_dobrej_terapii.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.centrum_dobrej_terapii.dtos.AppointmentDTO;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("Select new com.example.centrum_dobrej_terapii.dtos.AppointmentDTO(a.start, a.end, a.details, a.appointmentStatus, a.doctor.firstname, a.doctor.lastname) from Appointment a inner join a.patient au where au.email = :email")
    List<AppointmentDTO> findAppointmentAndDoctorFirstNameAndLastnameByPatientEmail(@Param("email") String email);

    @Query("Select new com.example.centrum_dobrej_terapii.dtos.AppointmentDTO(a.start, a.end, a.details, a.appointmentStatus) from Appointment a inner join a.patient au where au.email = :email")
    List<AppointmentDTO> findByDoctorEmail(@Param("email") String email);

}
