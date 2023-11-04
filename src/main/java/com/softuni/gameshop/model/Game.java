package com.softuni.gameshop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @ManyToOne
    private Genre genre;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer releaseYear;

    @Column(nullable = false)
    private Double price;
    // Other fields, getters, and setters

    @Column(nullable = false)
    private String imageURL;

    @Column(nullable = false)
    private String videoURL;

    public Game() {
    }

    public Long getId() {
        return id;
    }

    public Game setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Game setTitle(String title) {
        this.title = title;
        return this;
    }

    public Genre getGenre() {
        return genre;
    }

    public Game setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Game setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public Game setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Game setPrice(Double price) {
        this.price = price;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Game setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public Game setVideoURL(String videoURL) {
        this.videoURL = videoURL;
        return this;
    }
}
