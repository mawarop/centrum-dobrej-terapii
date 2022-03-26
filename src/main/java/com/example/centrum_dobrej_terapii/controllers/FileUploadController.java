package com.example.centrum_dobrej_terapii.controllers;


import com.example.centrum_dobrej_terapii.services.FileUploadService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("api/documents")
@AllArgsConstructor
public class FileUploadController {
    private final FileUploadService fileUploadService;


    @PostMapping("/upload")
    ResponseEntity<?> handleFileUpload(@RequestParam("userPesel") String userPesel, @RequestParam("file") MultipartFile file) throws IOException {
        boolean succesfullUpload = fileUploadService.handleFileUpload(file, userPesel);
        if (succesfullUpload) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{filename}")
    void handleFileDownload(HttpServletResponse httpServletResponse, @PathVariable("filename") String filename) {
        fileUploadService.handleFileDownload(httpServletResponse, filename);
    }
}
