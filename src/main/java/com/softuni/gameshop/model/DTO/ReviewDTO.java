package com.softuni.gameshop.model.DTO;

public class ReviewDTO {

    private Long id;

    private String textContent;

    private int stars;

    private String author;

    private String created;

    public ReviewDTO() {
    }

    public Long getId() {
        return id;
    }

    public ReviewDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public ReviewDTO setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getCreated() {
        return created;
    }

    public ReviewDTO setCreated(String created) {
        this.created = created;
        return this;
    }

    public String getTextContent() {
        return textContent;
    }

    public ReviewDTO setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }

    public int getStars() {
        return stars;
    }

    public ReviewDTO setStars(int stars) {
        this.stars = stars;
        return this;
    }
}
