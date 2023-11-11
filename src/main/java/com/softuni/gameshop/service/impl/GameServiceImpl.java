package com.softuni.gameshop.service.impl;

import com.softuni.gameshop.model.DTO.game.AddGameDTO;
import com.softuni.gameshop.model.DTO.game.GameCardDTO;
import com.softuni.gameshop.model.DTO.game.GameDetailsDTO;
import com.softuni.gameshop.model.Game;
import com.softuni.gameshop.model.Genre;
import com.softuni.gameshop.model.enums.GenreNamesEnum;
import com.softuni.gameshop.repository.GameRepository;
import com.softuni.gameshop.repository.GenreRepository;
import com.softuni.gameshop.service.GameService;
import com.softuni.gameshop.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Page<Game> undeletedGamesPage = gameRepository.findAllNotDeletedOrderByReleaseYearDesc(pageable);
        return undeletedGamesPage.map(this::mapAsCard);
    }

    @Override
    public void deleteGame(Long id) {
        Optional<Game> gameOptional = gameRepository.findById(id);

        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            game.setDeleted(true);
            gameRepository.save(game);
        }
    }

    @Override
    public Page<GameCardDTO> getGamesByGenre(GenreNamesEnum selectedGenre, Pageable pageable) {
        Page<Game> byGenre = this.gameRepository.findByGenre(selectedGenre, pageable);
        return byGenre.map(this::mapAsCard);
    }

    @Override
    public boolean exists(String title) {
        if (this.gameRepository.findByTitle(title).isPresent()){
            return true;
        } else {
            return false;
        }
    }


    public GameCardDTO mapAsCard(Game game){
        return modelMapper.map(game, GameCardDTO.class);
    }
}
