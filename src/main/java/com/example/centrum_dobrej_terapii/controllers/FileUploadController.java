package com.example.centrum_dobrej_terapii.controllers;


import com.example.centrum_dobrej_terapii.UserRole;
import com.example.centrum_dobrej_terapii.entities.AppUser;
import com.example.centrum_dobrej_terapii.entities.Appointment;
import com.example.centrum_dobrej_terapii.entities.Document;
import com.example.centrum_dobrej_terapii.repositories.AppUserRepository;
import com.example.centrum_dobrej_terapii.repositories.DocumentRepository;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@RequestMapping("api/documents")
@AllArgsConstructor
public class FileUploadController {

    private final DocumentRepository documentRepository;
    private final AppUserRepository appUserRepository;

    @PostMapping("/upload")
    ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("doctor_email") String doctor_email) throws IOException {
        if(!file.isEmpty()){

            try {

                String realPath = Paths.get("./upload/").toAbsolutePath().normalize().toString();
        System.out.println(realPath);
                if (!new File(realPath).exists()) {
                    new File(realPath).mkdir();
                }

                File dest = new File(realPath + "/" +file.getOriginalFilename());
                file.transferTo(dest);


                Optional<AppUser> doctor = appUserRepository.findByEmail(doctor_email);
                Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();

                if(doctor.isPresent() && doctor.get().getUserRole().name().equals(UserRole.DOCTOR.name()))
                {
                    Document document = new Document(dest.getPath(), dest.getName(),doctor.get(),(AppUser) principal);
                    documentRepository.save(document);
                }
                else
                {
                    throw new IllegalStateException("there is no doctor with this email");

                }


            }
            catch (IOException ioException){
                System.out.println(ioException.getMessage());
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            catch (IllegalStateException illegalStateException){
                System.out.println(illegalStateException.getMessage());
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/{filename}")
    void handleFileDownload(HttpServletResponse httpServletResponse, @PathVariable("filename") String filename){
        Optional<Document> document = documentRepository.findByName(filename);
        try{
        if(document.isPresent())
        {
            httpServletResponse.setContentType("application/octet-stream");
            httpServletResponse.addHeader("Content-Disposition", "attachment; filename=" + filename);
            FileInputStream fileInputStream = new FileInputStream(document.get().getPath());
            IOUtils.copy(fileInputStream, httpServletResponse.getOutputStream());
            httpServletResponse.getOutputStream().flush();
            httpServletResponse.getOutputStream().close();
        }
        else{
            throw new NoSuchFileException("Nie znaleziono pliku");
        }
        }

        catch (NoSuchFileException noSuchFileException){
            System.out.println(noSuchFileException.getMessage());
        }
        catch (IOException ioException)
        {
            System.out.println(ioException.getMessage());
        }

//        FileSystemResource fileSystemResource = new FileSystemResource(new File(document.getPath()));
//            return fileSystemResource;
        }
}
