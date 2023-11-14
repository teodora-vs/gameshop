package com.softuni.gameshop.service.impl;

import com.softuni.gameshop.model.DTO.game.AddGameDTO;
import com.softuni.gameshop.model.DTO.game.EditGameDTO;
import com.softuni.gameshop.model.DTO.game.GameCardDTO;
import com.softuni.gameshop.model.DTO.game.GameDetailsDTO;
import com.softuni.gameshop.model.Game;
import com.softuni.gameshop.model.Genre;
import com.softuni.gameshop.model.Review;
import com.softuni.gameshop.model.enums.GenreNamesEnum;
import com.softuni.gameshop.repository.GameRepository;
import com.softuni.gameshop.repository.GenreRepository;
import com.softuni.gameshop.repository.ReviewRepository;
import com.softuni.gameshop.service.GameService;
import com.softuni.gameshop.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private ModelMapper modelMapper;
    private GameRepository gameRepository;
    private GenreRepository genreRepository;
    private ReviewRepository reviewRepository;

    public GameServiceImpl(ModelMapper modelMapper, GameRepository gameRepository, GenreRepository genreRepository, ReviewRepository reviewRepository) {
        this.modelMapper = modelMapper;
        this.gameRepository = gameRepository;
        this.genreRepository = genreRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Long addGame(AddGameDTO addGameDTO) {
        Game game = this.modelMapper.map(addGameDTO, Game.class);
        Genre genre = genreRepository.findByName(addGameDTO.getGenre()).get();
        game.setGenre(genre);
        game.setReviews(new ArrayList<>());
        this.gameRepository.save(game);

        return game.getId();
    }

    @Override
    public GameDetailsDTO getGameDetails(Long id) {

        Game byId = this.gameRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Game with id: " + id + " not found!"));
        GameDetailsDTO gameDetailsDTO = modelMapper.map(byId, GameDetailsDTO.class);
        gameDetailsDTO.setReviews(this.reviewRepository.findByGameId(id));
        return gameDetailsDTO;
    }

    public EditGameDTO convertToEditGameDTO(GameDetailsDTO gameDetailsDTO){
        EditGameDTO map = modelMapper.map(gameDetailsDTO, EditGameDTO.class);
        return map;
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
    public void editGame(Long id, EditGameDTO editGameDTO) {
        Game existingGame = this.gameRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Game with id: " + id + " not found!"));

        // Map fields from the EditGameDTO to the existing Game entity
        this.modelMapper.map(editGameDTO, existingGame);

        // If the genre is also editable, you can update it as well
        Genre genre = this.genreRepository.findByName(editGameDTO.getGenreName()).orElse(null);
        existingGame.setGenre(genre);

        // Save the updated game entity
        this.gameRepository.save(existingGame);
    }

    @Override
    public Page<GameCardDTO> getGamesByGenre(GenreNamesEnum selectedGenre, Pageable pageable) {
        Page<Game> byGenre = this.gameRepository.findByGenre(selectedGenre, pageable);
        return byGenre.map(this::mapAsCard);
    }

    @Override
    public Double getAverageScore(Long gameId) {
        List<Review> reviews = reviewRepository.findByGameId(gameId);

        if (reviews.isEmpty()) {
            return 0.0; // or any default value you prefer
        }

        double totalScore = 0.0;
        for (Review review : reviews) {
            totalScore += review.getStars();
        }

        return totalScore / reviews.size();
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
