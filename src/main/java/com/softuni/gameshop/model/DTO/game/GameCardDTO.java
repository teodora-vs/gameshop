package com.softuni.gameshop.model.DTO.game;

import com.softuni.gameshop.model.enums.GenreNamesEnum;

import java.math.BigDecimal;

public class GameCardDTO {
    private Long id;

    private String title;

    private GenreNamesEnum genreName;

    private Integer releaseYear;

    private BigDecimal price;

    private String imageURL;

    public GameCardDTO() {
    }

    public Long getId() {
        return id;
    }

    public GameCardDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public GameCardDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public GenreNamesEnum getGenreName() {
        return genreName;
    }

    public GameCardDTO setGenreName(GenreNamesEnum genreName) {
        this.genreName = genreName;
        return this;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public GameCardDTO setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public GameCardDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public GameCardDTO setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }
}
