package com.example.centrum_dobrej_terapii.services;

import com.example.centrum_dobrej_terapii.dtos.NamePathFileResponse;
import com.example.centrum_dobrej_terapii.repositories.DocumentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class DocumentServiceImpl implements  DocumentService{
    private final DocumentRepository documentRepository;
    private final String URL ="http://localhost:8080/api/documents/";
    @Override
    public List<NamePathFileResponse> getPatientDocuments(String pesel) {
        List<String> patientDocumentsNames = documentRepository.findPathsByPatientPesel(pesel);
        if(patientDocumentsNames.isEmpty()){
            throw new IllegalStateException("Użytkownik nie posiada dokumentów lub podany pesel jest nieprawidłowy");
        }
        List<NamePathFileResponse> namePathFileResponseList = new ArrayList<NamePathFileResponse>();
        for (String name :patientDocumentsNames){
            namePathFileResponseList.add(new NamePathFileResponse(name, URL + name));
        }
        return namePathFileResponseList;
    }
}
