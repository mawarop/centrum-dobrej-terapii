package com.example.centrum_dobrej_terapii.repositories;

import com.example.centrum_dobrej_terapii.AppointmentStatus;
import com.example.centrum_dobrej_terapii.dtos.AppointmentResponse;
import com.example.centrum_dobrej_terapii.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("Select new com.example.centrum_dobrej_terapii.dtos.AppointmentResponse(a.id, a.start, a.end, a.details, a.appointmentStatus, a.doctor.firstname, a.doctor.lastname) from Appointment a inner join a.patient au where au.email = :email")
    List<AppointmentResponse> findAppointmentAndDoctorFirstNameAndLastnameByPatientEmail(@Param("email") String email);

//    @Query("Select new com.example.centrum_dobrej_terapii.dtos.AppointmentBaseResponse(a.id, a.start, a.end, a.details, a.appointmentStatus) from Appointment a inner join a.patient au where au.email = :email")
//    List<AppointmentBaseResponse> findByDoctorEmail(@Param("email") String email);

    @Query("Select new com.example.centrum_dobrej_terapii.dtos.AppointmentResponse(a.id, a.start, a.end, a.details, a.appointmentStatus, a.patient.firstname, a.patient.lastname) from Appointment a inner join a.doctor au where au.email = :email")
    List<AppointmentResponse> findAppointmentAndPatientFirstNameAndLastnameByDoctorEmail(@Param("email") String email);
    @Query("select new com.example.centrum_dobrej_terapii.dtos.AppointmentResponse(a.id, a.start, a.end, a.details, a.appointmentStatus, a.doctor.firstname, a.doctor.lastname) from Appointment a inner join a.doctor au where au.email = :email AND a.appointmentStatus=:status")
    List<AppointmentResponse> findAppointmentsByDoctorEmailAndAppointmentStatus(@Param("email") String email, @Param("status") AppointmentStatus status);
}
