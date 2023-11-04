package com.softuni.gameshop.repository;

import com.softuni.gameshop.model.Genre;
import com.softuni.gameshop.model.enums.GenreNamesEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findByName(GenreNamesEnum genreName);
}
