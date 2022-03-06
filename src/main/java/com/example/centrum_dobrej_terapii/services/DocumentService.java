package com.example.centrum_dobrej_terapii.services;

import com.example.centrum_dobrej_terapii.dtos.NamePathFileResponse;

import java.util.List;

public interface DocumentService {
    List<NamePathFileResponse> getAllPatientDocuments(String pesel);
    List<NamePathFileResponse> getPatientDocumentsByLoggedInDoctor(String patientPesel, String doctorPesel);

}
