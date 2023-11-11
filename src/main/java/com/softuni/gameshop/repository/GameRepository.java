package com.softuni.gameshop.repository;

import com.softuni.gameshop.model.Game;
import com.softuni.gameshop.model.Genre;
import com.softuni.gameshop.model.enums.GenreNamesEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("SELECT g FROM Game g WHERE g.isDeleted = false ORDER BY g.releaseYear DESC")
    Page<Game> findAllNotDeletedOrderByReleaseYearDesc(Pageable pageable);

    @Query("SELECT g FROM Game g WHERE g.genre.name = :genreName")
    Page<Game> findByGenre(GenreNamesEnum genreName, Pageable pageable);

    Optional<Game> findByTitle(String title);

}
