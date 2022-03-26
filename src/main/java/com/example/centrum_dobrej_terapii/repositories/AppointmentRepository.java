package com.example.centrum_dobrej_terapii.repositories;

import com.example.centrum_dobrej_terapii.AppointmentStatus;
import com.example.centrum_dobrej_terapii.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAppointmentsByPatientEmail(String email);

    List<Appointment> findAppointmentsByDoctorEmail(String email);

    @Query("select a from Appointment a inner join a.doctor au where au.email = :email AND a.appointmentStatus=:status")
    List<Appointment> findAppointmentsByDoctorEmailAndAppointmentStatus(@Param("email") String email, @Param("status") AppointmentStatus status);

    @Query("select a from Appointment a inner join a.patient au where au.email = :email AND a.appointmentStatus=:status")
    List<Appointment> findAppointmentsByPatientEmailAndAppointmentStatus(@Param("email") String email, @Param("status") AppointmentStatus status);

}
