package com.softuni.gameshop.service;

import com.softuni.gameshop.model.DTO.game.AddGameDTO;
import com.softuni.gameshop.model.DTO.game.EditGameDTO;
import com.softuni.gameshop.model.DTO.game.GameCardDTO;
import com.softuni.gameshop.model.DTO.game.GameDetailsDTO;
import com.softuni.gameshop.model.enums.GenreNamesEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GameService {
    Long addGame(AddGameDTO addGameDTO);

    GameDetailsDTO getGameDetails(Long offerId) ;

    EditGameDTO convertToEditGameDTO(GameDetailsDTO gameDetailsDTO);

    Page<GameCardDTO> getAllGames(Pageable pageable);

    void deleteGame(Long id);

    void editGame(Long id, EditGameDTO editGameDTO);

    Double getAverageScore(Long gameId);

    Page<GameCardDTO> getGamesByGenre(GenreNamesEnum selectedGenre, Pageable pageable);

    boolean exists(String title);
}
