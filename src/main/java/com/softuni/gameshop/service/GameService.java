package com.softuni.gameshop.service;

import com.softuni.gameshop.model.DTO.game.AddGameDTO;
import com.softuni.gameshop.model.DTO.game.EditGameDTO;
import com.softuni.gameshop.model.DTO.game.GameSummaryDTO;
import com.softuni.gameshop.model.DTO.game.GameDetailsDTO;
import com.softuni.gameshop.model.enums.GenreNamesEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GameService {
    Long addGame(AddGameDTO addGameDTO);

    GameDetailsDTO getGameDetails(Long offerId) ;

    EditGameDTO convertToEditGameDTO(GameDetailsDTO gameDetailsDTO);

    Page<GameSummaryDTO> getAllGames(Pageable pageable);

    void deleteGame(Long id);

    void editGame(Long id, EditGameDTO editGameDTO);

    Double getAverageScore(Long gameId);

    Page<GameSummaryDTO> getGamesByGenre(GenreNamesEnum selectedGenre, Pageable pageable);

    boolean exists(String title);

    boolean existsWithSameTitle(Long id, String gameTitle);
}
