package com.example.centrum_dobrej_terapii.services;

import com.example.centrum_dobrej_terapii.dtos.NamePathFileResponse;
import com.example.centrum_dobrej_terapii.repositories.DocumentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final String URL = "http://localhost:8080/api/documents/";


    @Override
    public List<NamePathFileResponse> getAllPatientDocuments(String pesel) {
        List<String> patientDocumentsNames = documentRepository.findPathsByPatientPesel(pesel);
        throwExceptionIfDocumentsListIsEmpty(patientDocumentsNames);
        return parseResponse(patientDocumentsNames);
    }

    private void throwExceptionIfDocumentsListIsEmpty(List<String> patientDocumentsNames) {
        if (patientDocumentsNames.isEmpty()) {
            throw new IllegalStateException("Użytkownik nie posiada dokumentów lub podany pesel jest nieprawidłowy");
        }
    }

    private List<NamePathFileResponse> parseResponse(List<String> patientDocumentsNames) {
        List<NamePathFileResponse> namePathFileResponseList = new ArrayList<NamePathFileResponse>();
        for (String name : patientDocumentsNames) {
            namePathFileResponseList.add(new NamePathFileResponse(name, URL + name));
        }
        return namePathFileResponseList;
    }


    @Override
    public List<NamePathFileResponse> getPatientDocumentsByLoggedInDoctor(String patientPesel, String doctorPesel) {
        List<String> patientDocumentsNames = documentRepository.findPathsByPatientPeselAndDoctorPesel(patientPesel, doctorPesel);
        throwExceptionIfDocumentsListIsEmpty(patientDocumentsNames);
        return parseResponse(patientDocumentsNames);
    }
}
