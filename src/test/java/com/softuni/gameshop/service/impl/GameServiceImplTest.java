package com.softuni.gameshop.service.impl;

import com.softuni.gameshop.model.DTO.game.AddGameDTO;
import com.softuni.gameshop.model.DTO.game.EditGameDTO;
import com.softuni.gameshop.model.DTO.game.GameDetailsDTO;
import com.softuni.gameshop.model.DTO.game.GameSummaryDTO;
import com.softuni.gameshop.model.Game;
import com.softuni.gameshop.model.Genre;
import com.softuni.gameshop.model.Review;
import com.softuni.gameshop.model.enums.GenreNamesEnum;
import com.softuni.gameshop.repository.GameRepository;
import com.softuni.gameshop.repository.GenreRepository;
import com.softuni.gameshop.repository.ReviewRepository;
import com.softuni.gameshop.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private GameServiceImpl gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddGame() {
        AddGameDTO addGameDTO = new AddGameDTO();
        addGameDTO.setTitle("Test Game");
        addGameDTO.setGenre(GenreNamesEnum.ADVENTURE);
        addGameDTO.setDescription("Test description");
        addGameDTO.setReleaseYear(2022);
        addGameDTO.setPrice(BigDecimal.valueOf(49.99));
        addGameDTO.setImageURL("test-image-url");
        addGameDTO.setVideoURL("test-video-url");

        Game game = new Game();
        game.setTitle("Test Game");

        when(modelMapper.map(addGameDTO, Game.class)).thenReturn(game);
        when(genreRepository.findByName(GenreNamesEnum.ADVENTURE)).thenReturn(Optional.of(new Genre()));
        when(gameRepository.save(any(Game.class))).thenReturn(game);

        Long gameId = gameService.addGame(addGameDTO);

        assertEquals(game.getId(), gameId);
        verify(gameRepository, times(1)).save(game);
    }


    @Test
    void testDeleteGame() {
        Long gameId = 1L;
        Game game = new Game().setId(gameId);
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));

        gameService.deleteGame(gameId);

        assertTrue(game.isDeleted());
        verify(gameRepository, times(1)).save(game);
    }

    @Test
    void testEditGame() {
        Long gameId = 1L;
        EditGameDTO editGameDTO = new EditGameDTO()
                .setTitle("Updated Game")
                .setGenre(GenreNamesEnum.ADVENTURE)
                .setDescription("Updated description")
                .setReleaseYear(2023)
                .setPrice(BigDecimal.valueOf(59.99))
                .setImageURL("updated-image-url")
                .setVideoURL("updated-video-url");

        Game existingGame = new Game()
                .setId(gameId)
                .setTitle("Existing Game")
                .setGenre(new Genre().setName(GenreNamesEnum.ADVENTURE));

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(existingGame));
        when(genreRepository.findByName(GenreNamesEnum.ADVENTURE)).thenReturn(Optional.of(new Genre()));

        gameService.editGame(gameId, editGameDTO);

        verify(gameRepository, times(1)).findById(gameId);
        verify(genreRepository, times(1)).findByName(GenreNamesEnum.ADVENTURE);

        verify(modelMapper, times(1)).map(eq(editGameDTO), same(existingGame));

        verify(gameRepository, times(1)).save(existingGame);
    }


    @Test
    void testGetGameDetails() {
        Long gameId = 1L;
        Game game = new Game()
                .setId(gameId)
                .setTitle("Test Game")
                .setGenre(new Genre().setName(GenreNamesEnum.ADVENTURE))
                .setDescription("Test description")
                .setReleaseYear(2022)
                .setPrice(BigDecimal.valueOf(49.99))
                .setImageURL("test-image-url")
                .setVideoURL("test-video-url");

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(modelMapper.map(game, GameDetailsDTO.class)).thenReturn(new GameDetailsDTO()
                .setTitle(game.getTitle()).setGenreName(game.getGenre().getName()).setReleaseYear(game.getReleaseYear())
                .setPrice(game.getPrice()).setImageURL(game.getImageURL()).setVideoURL(game.getVideoURL()).setDescription(game.getDescription())
                .setId(gameId));

        GameDetailsDTO gameDetailsDTO = gameService.getGameDetails(gameId);

        assertNotNull(gameDetailsDTO);
        assertEquals(game.getTitle(), gameDetailsDTO.getTitle());
        assertEquals(game.getGenre().getName(), gameDetailsDTO.getGenreName());
        assertEquals(game.getDescription(), gameDetailsDTO.getDescription());
        assertEquals(game.getReleaseYear(), gameDetailsDTO.getReleaseYear());
        assertEquals(game.getPrice(), gameDetailsDTO.getPrice());
        assertEquals(game.getImageURL(), gameDetailsDTO.getImageURL());
        assertEquals(game.getVideoURL(), gameDetailsDTO.getVideoURL());

        verify(modelMapper, times(1)).map(game, GameDetailsDTO.class);
    }

    @Test
    void testGetGameDetailsNotFound() {
        Long gameId = 1L;
        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> gameService.getGameDetails(gameId));
    }

    @Test
    void testDeleteGameNotFound() {
        Long gameId = 1L;
        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> gameService.deleteGame(gameId));
    }

    @Test
    void testGetAverageScore() {
        Long gameId = 1L;
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review().setStars(4));
        reviews.add(new Review().setStars(5));

        when(reviewRepository.findAllByGameId(gameId)).thenReturn(reviews);

        Double result = gameService.getAverageScore(gameId);

        assertEquals(4.5, result, 0.01);
    }

    @Test
    void testGetAllGames() {
        Pageable pageable = mock(Pageable.class);
        List<Game> gamesList = new ArrayList<>();
        gamesList.add(new Game());
        gamesList.add(new Game());
        Page<Game> gamesPage = new PageImpl<>(gamesList);

        when(gameRepository.findAllNotDeletedOrderByReleaseYearDesc(pageable)).thenReturn(gamesPage);

        Page<GameSummaryDTO> result = gameService.getAllGames(pageable);

        assertEquals(gamesList.size(), result.getContent().size());
        verify(modelMapper, times(gamesList.size())).map(any(Game.class), eq(GameSummaryDTO.class));
    }

    @Test
    void testGetGamesByGenre() {
        GenreNamesEnum selectedGenre = GenreNamesEnum.ADVENTURE;
        Pageable pageable = mock(Pageable.class);
        List<Game> gamesList = new ArrayList<>();
        gamesList.add(new Game());
        gamesList.add(new Game());
        Page<Game> gamesPage = new PageImpl<>(gamesList);

        when(gameRepository.findByGenre(selectedGenre, pageable)).thenReturn(gamesPage);

        Page<GameSummaryDTO> result = gameService.getGamesByGenre(selectedGenre, pageable);

        assertEquals(gamesList.size(), result.getContent().size());
        verify(modelMapper, times(gamesList.size())).map(any(Game.class), eq(GameSummaryDTO.class));
    }

    @Test
    void testExists() {
        String title = "Test Game";
        when(gameRepository.findByTitle(title)).thenReturn(Optional.of(new Game()));

        boolean result = gameService.exists(title);

        assertTrue(result);
        verify(gameRepository, times(1)).findByTitle(title);
    }

}
