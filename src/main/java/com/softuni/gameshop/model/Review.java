package com.softuni.gameshop.model;

import jakarta.persistence.*;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @ManyToOne
    private UserEntity user;
    @ManyToOne
    private Game game;
    // Other fields, getters, and setters
}
