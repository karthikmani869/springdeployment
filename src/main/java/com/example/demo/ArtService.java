package com.example.demo;

import com.example.demo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ArtService {

    @Autowired
    private ArtRepository artRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public Art saveArt(MultipartFile picture, String artistName, String year, String description) throws IOException {
        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename
        String originalFilename = picture.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString() + fileExtension;

        // Save file
        Path filePath = uploadPath.resolve(newFilename);
        Files.copy(picture.getInputStream(), filePath);

        // Create and save Art entity
        Art art = new Art();

        art.setArtistName(artistName);
        art.setYear(year);
        art.setDescription(description);
        art.setFileName(newFilename);
        art.setImageUrl("/uploads/" + newFilename);

        return artRepository.save(art);
    }

    public List<Art> getAllArt() {
        return artRepository.findAll();
    }
    public Art getArtById(Long id) {
        return artRepository.findById(id).orElse(null);
    }

	public ArtRepository getArtRepository() {
		return artRepository;
	}

	public void setArtRepository(ArtRepository artRepository) {
		this.artRepository = artRepository;
	}

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}
    
}