package com.softuni.gameshop.model;

import jakarta.persistence.*;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate orderDate;

    @ManyToOne
    private UserEntity user;

    @ManyToMany
    private List<Game> games;

    public Order() {
    }
}
