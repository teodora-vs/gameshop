package com.softuni.gameshop.model.DTO;

import com.softuni.gameshop.model.enums.GenreNamesEnum;

public class GameDetailsDTO {

    private Long id;

    private String title;

    private GenreNamesEnum genreName;

    private String description;

    private Integer releaseYear;

    private Double price;

    private String imageURL;

    private String videoURL;

    private Boolean isDeleted;

    public GameDetailsDTO() {
    }

    public Long getId() {
        return id;
    }

    public GameDetailsDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public GameDetailsDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public GenreNamesEnum getGenreName() {
        return genreName;
    }

    public GameDetailsDTO setGenreName(GenreNamesEnum genreName) {
        this.genreName = genreName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public GameDetailsDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public GameDetailsDTO setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public GameDetailsDTO setPrice(Double price) {
        this.price = price;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public GameDetailsDTO setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public GameDetailsDTO setVideoURL(String videoURL) {
        this.videoURL = videoURL;
        return this;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public GameDetailsDTO setDeleted(Boolean deleted) {
        isDeleted = deleted;
        return this;
    }
}
