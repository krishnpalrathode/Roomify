package com.hotelmanagement.roomify.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
    // Path to your existing static Images folder
    private final String uploadDir = "uploads/Images/";

    public String saveFileLocally(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        // Create folder if it doesn't exist (optional if you already have it)
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename();
        Path filePath = uploadPath.resolve(originalFilename);

        // Save file, overwrite if already exists
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Return relative URL path to use in your API response
        return "/Images/" + originalFilename;
    }
}
