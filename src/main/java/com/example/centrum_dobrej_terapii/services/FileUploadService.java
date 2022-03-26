package com.example.centrum_dobrej_terapii.services;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface FileUploadService {
    boolean handleFileUpload(MultipartFile file, String userPesel);

    void handleFileDownload(HttpServletResponse httpServletResponse, String filename);
}
