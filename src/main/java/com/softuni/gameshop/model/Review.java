package com.softuni.gameshop.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(name = "text_content",nullable = false, columnDefinition = "TEXT")
    private String textContent;

    @Column(nullable = false)
    private Integer stars;

    @ManyToOne
    private UserEntity author;

    @ManyToOne
    private Game game;

    public Review() {
    }

    public Long getId() {
        return id;
    }

    public Review setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTextContent() {
        return textContent;
    }

    public Review setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }

    public int getStars() {
        return stars;
    }

    public Review setStars(int stars) {
        this.stars = stars;
        return this;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public Review setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    public Game getGame() {
        return game;
    }

    public Review setGame(Game game) {
        this.game = game;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public Review setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }
}
