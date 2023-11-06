package com.softuni.gameshop.service;

import com.softuni.gameshop.model.DTO.AddGameDTO;
import com.softuni.gameshop.model.DTO.GameCardDTO;
import com.softuni.gameshop.model.DTO.GameDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GameService {
    Long addGame(AddGameDTO addGameDTO);

    GameDetailsDTO getGameDetails(Long offerId) ;

    Page<GameCardDTO> getAllGames(Pageable pageable);

    void deleteGame(Long id);
}
