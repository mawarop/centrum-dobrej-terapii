package com.example.centrum_dobrej_terapii.repositories;

import com.example.centrum_dobrej_terapii.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByName(String name);

    @Query("select d.name from Document d where d.patient.pesel = :pesel")
    List<String> findPathsByPatientPesel(@Param("pesel") String pesel);

    @Query("select d.name from Document d where d.patient.pesel = :patient_pesel AND d.doctor.pesel = :doctor_pesel")
    List<String> findPathsByPatientPeselAndDoctorPesel(
            @Param("patient_pesel") String patientPesel,
            @Param("doctor_pesel") String doctorPesel
    );

}
