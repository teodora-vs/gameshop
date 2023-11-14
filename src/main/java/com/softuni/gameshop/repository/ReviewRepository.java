package com.softuni.gameshop.repository;

import com.softuni.gameshop.model.Game;
import com.softuni.gameshop.model.Review;
import com.softuni.gameshop.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByGameId(Long gameId);


    Optional<Review> findByAuthorIdAndGameId(Long id, Long gameId);
}
