package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/art")
@CrossOrigin(origins = "http://localhost:5173") // For Vite's default port
public class ArtController {

    @Autowired
    private ArtService artService;

    // Endpoint to upload a new art piece with an image
    @PostMapping
    public ResponseEntity<?> uploadArt(
            @RequestParam("picture") MultipartFile picture,
            @RequestParam("artistName") String artistName,
            @RequestParam("year") String year,
            @RequestParam("description") String description) {
        try {
            Art savedArt = artService.saveArt(picture, artistName, year, description);
            return ResponseEntity.ok(savedArt);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error uploading art: " + e.getMessage());
        }
    }

    // Endpoint to get a list of all art pieces
    @GetMapping
    public ResponseEntity<List<Art>> getAllArt() {
        return ResponseEntity.ok(artService.getAllArt());
    }

    // Endpoint to retrieve an image by file name
    @GetMapping("/image/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) {
        Path imagePath = Paths.get(artService.getUploadDir()).resolve(fileName);
        try {
            byte[] imageBytes = Files.readAllBytes(imagePath);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
   

}
