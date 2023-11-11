package com.softuni.gameshop.service;

import com.softuni.gameshop.model.DTO.game.AddGameDTO;
import com.softuni.gameshop.model.DTO.game.GameCardDTO;
import com.softuni.gameshop.model.DTO.game.GameDetailsDTO;
import com.softuni.gameshop.model.enums.GenreNamesEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GameService {
    Long addGame(AddGameDTO addGameDTO);

    GameDetailsDTO getGameDetails(Long offerId) ;

    Page<GameCardDTO> getAllGames(Pageable pageable);

    void deleteGame(Long id);

    Page<GameCardDTO> getGamesByGenre(GenreNamesEnum selectedGenre, Pageable pageable);

    boolean exists(String title);
}
