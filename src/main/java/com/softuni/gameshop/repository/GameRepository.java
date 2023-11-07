package com.softuni.gameshop.repository;

import com.softuni.gameshop.model.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Page<Game> findByIsDeletedFalse(Pageable pageable);
}
