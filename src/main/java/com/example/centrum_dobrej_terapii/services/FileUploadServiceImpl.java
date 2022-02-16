package com.example.centrum_dobrej_terapii.services;

import com.example.centrum_dobrej_terapii.UserRole;
import com.example.centrum_dobrej_terapii.entities.AppUser;
import com.example.centrum_dobrej_terapii.entities.Document;
import com.example.centrum_dobrej_terapii.repositories.AppUserRepository;
import com.example.centrum_dobrej_terapii.repositories.DocumentRepository;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FileUploadServiceImpl implements FileUploadService{
    private final DocumentRepository documentRepository;
    private final AppUserRepository appUserRepository;

    //handleFileUpload functions

    File saveFileOnDisk(MultipartFile file) throws IOException {
        String realPath = Paths.get("./upload/").toAbsolutePath().normalize().toString();
        if (!new File(realPath).exists()) {
            new File(realPath).mkdir();
        }

        File dest = new File(realPath + "/" +file.getOriginalFilename());
        file.transferTo(dest);
        return dest;
    }

    boolean isDoctor(AppUser user){
        return user.getUserRole().name().equals(UserRole.DOCTOR.name());
    }

    void saveDocument(File dest, AppUser doctor, AppUser principal){
        Document document = new Document(dest.getPath(), dest.getName(), doctor, principal);
        documentRepository.save(document);
    }
    boolean isPatient(AppUser user){
        return user.getUserRole().name().equals(UserRole.PATIENT.name());
    }

    // handlefileDownload functions
    boolean isAdmin(AppUser user){
        return user.getUserRole().name().equals(UserRole.ADMIN.name());
    }

    boolean hasAuthorityToDownloadDocument(AppUser user, Document document){
        return document.getDoctor().equals(user);
    }
    boolean isAuthorizedUser(AppUser user, Document document){
        return  hasAuthorityToDownloadDocument( user, document) || isAdmin(user);
    }

    void fileDownloadingHandler(HttpServletResponse httpServletResponse, Document document, String filename){
        try {
            httpServletResponse.setContentType("application/octet-stream");
            httpServletResponse.addHeader("Content-Disposition", "attachment; filename=" + filename);
            FileInputStream fileInputStream = new FileInputStream(document.getPath());
            IOUtils.copy(fileInputStream, httpServletResponse.getOutputStream());
            httpServletResponse.getOutputStream().flush();
            httpServletResponse.getOutputStream().close();
        }catch (IOException ioException)
        {
            System.out.println(ioException.getMessage());
        }
    }

    @Override
    public boolean handleFileUpload(MultipartFile file, String userPesel) {
        if(!file.isEmpty()){
            try {
                File dest = saveFileOnDisk(file);

                Optional<AppUser> optionalPatient = appUserRepository.findByPesel(userPesel);
                AppUser doctor = (AppUser) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();

                if(optionalPatient.isPresent() && isPatient(optionalPatient.get()) && isDoctor(doctor))
                {
                    saveDocument(dest, doctor, optionalPatient.get());
                }
                else
                {
                    throw new IllegalStateException("there is no patient with this email");
                }
            }

            catch (IOException ioException){
                System.out.println(ioException.getMessage());
                return false;
            }
            catch (IllegalStateException illegalStateException){
                System.out.println(illegalStateException.getMessage());
                return false;
            }

        }
        return true;
    }

    @Override
    public void handleFileDownload(HttpServletResponse httpServletResponse, String filename) {
        Optional<Document> optionalDocument = documentRepository.findByName(filename);
        AppUser principal = (AppUser) SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();

        try{
            if(optionalDocument.isPresent() && isAuthorizedUser(principal, optionalDocument.get()))
            {
                fileDownloadingHandler(httpServletResponse, optionalDocument.get(), filename);
            }
            else{
                throw new NoSuchFileException("Nie znaleziono pliku");
            }

        }
        catch (NoSuchFileException noSuchFileException){
            System.out.println(noSuchFileException.getMessage());
        }
    }
    }

