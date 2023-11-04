package com.softuni.gameshop.model;

import com.softuni.gameshop.model.enums.GenreNamesEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private GenreNamesEnum name;

    public Genre() {
    }

    public Long getId() {
        return id;
    }

    public Genre setId(Long id) {
        this.id = id;
        return this;
    }

    public GenreNamesEnum getName() {
        return name;
    }

    public Genre setName(GenreNamesEnum name) {
        this.name = name;
        return this;
    }
}
