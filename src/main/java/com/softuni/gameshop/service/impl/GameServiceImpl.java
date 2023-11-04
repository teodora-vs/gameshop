package com.softuni.gameshop.service.impl;

import com.softuni.gameshop.model.DTO.AddGameDTO;
import com.softuni.gameshop.model.DTO.GameCardDTO;
import com.softuni.gameshop.model.DTO.GameDetailsDTO;
import com.softuni.gameshop.model.Game;
import com.softuni.gameshop.model.Genre;
import com.softuni.gameshop.repository.GameRepository;
import com.softuni.gameshop.repository.GenreRepository;
import com.softuni.gameshop.service.GameService;
import com.softuni.gameshop.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    private ModelMapper modelMapper;
    private GameRepository gameRepository;
    private GenreRepository genreRepository;

    public GameServiceImpl(ModelMapper modelMapper, GameRepository gameRepository, GenreRepository genreRepository) {
        this.modelMapper = modelMapper;
        this.gameRepository = gameRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public Long addGame(AddGameDTO addGameDTO) {
        Game game = this.modelMapper.map(addGameDTO, Game.class);
        Genre genre = genreRepository.findByName(addGameDTO.getGenre()).get();
        game.setGenre(genre);
        this.gameRepository.save(game);

        return game.getId();
    }

    @Override
    public GameDetailsDTO getGameDetails(Long id) {

        Game byId = gameRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Game with id: " + id + " not found!"));
        GameDetailsDTO gameDetailsDTO = modelMapper.map(byId, GameDetailsDTO.class);
        return gameDetailsDTO;
    }

    @Override
    public Page<GameCardDTO> getAllGames(Pageable pageable) {
       return gameRepository.findAll(pageable).map(this::mapAsCard);
    }

    public GameCardDTO mapAsCard(Game game){
        return modelMapper.map(game, GameCardDTO.class);
    }
}
