package com.softuni.gameshop.repository;

import com.softuni.gameshop.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findAllByGameId(Long gameId);

    @Query("SELECT r FROM Review r WHERE r.game.id = :gameId ORDER BY r.created DESC")
    List<Review> findAllByGameIdOrderByMostRecent(Long gameId);

}
