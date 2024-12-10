package com.example.demo;

public class ArtResponse {
    private Long id;
    private String artistName;
    private String year;
    private String description;
    private String imageUrl;

    public ArtResponse(Long id, String artistName, String year, String description, String imageUrl) {
        this.id = id;
        this.artistName = artistName;
        this.year = year;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    // Getters and setters
}
