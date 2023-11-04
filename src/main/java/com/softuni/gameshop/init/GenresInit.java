package com.softuni.gameshop.init;

import com.softuni.gameshop.model.Genre;
import com.softuni.gameshop.model.enums.GenreNamesEnum;
import com.softuni.gameshop.repository.GenreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GenresInit implements CommandLineRunner {

    private final GenreRepository genreRepository;

    public GenresInit(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (genreRepository.count() == 0) {
            for (GenreNamesEnum name : GenreNamesEnum.values()) {
                Genre genre = new Genre();
                genre.setName(name);
                genreRepository.save(genre);
            }
        }
    }

}

