package com.dilruk.movieticketbooking.api;

import com.dilruk.movieticketbooking.exceptions.MovieAlreadyExistsException;
import com.dilruk.movieticketbooking.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.mappers.MovieMapper;
import com.dilruk.movieticketbooking.api.request.MovieRequest;
import com.dilruk.movieticketbooking.dtos.MovieDTO;
import com.dilruk.movieticketbooking.services.movie.MovieServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling movie-related requests, such as creating, updating, retrieving, and deleting movies.
 */
@Slf4j
@RestController
@RequestMapping("api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MovieServiceImpl movieService;
    private final MovieMapper movieMapper;

    /**
     * Creates a new movie.
     *
     * @param movie the movie details to be created, provided in the request body
     * @return ResponseEntity containing the created movie details or error response
     */
    @PostMapping
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieRequest movie) {
        try {
            MovieDTO savedMovie = movieService.createMovie(movieMapper.fromRequestToDto(movie));
            return ResponseEntity.ok(savedMovie);
        } catch (MovieAlreadyExistsException e) {
            logger.info("Movie already exists: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error occurred while creating movie: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Retrieves all movies.
     *
     * @return ResponseEntity containing a list of all movies
     */
    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        List<MovieDTO> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

    /**
     * Retrieves a movie by its ID.
     *
     * @param id the ID of the movie to retrieve
     * @return ResponseEntity containing the movie details or not found response
     */
    @GetMapping("{id}")
    public ResponseEntity<MovieDTO> getMovie(@PathVariable String id) {
        try {
            MovieDTO movie = movieService.getMovieById(id);
            return ResponseEntity.ok(movie);
        } catch (UserNotFoundException e) {
            logger.info("Movie not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates an existing movie's details.
     *
     * @param id the ID of the movie to update
     * @param request the updated movie details to be applied
     * @return ResponseEntity containing the updated movie details or not found response
     */
    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable String id, @RequestBody MovieRequest request) {
        try {
            MovieDTO updatedMovie = movieService.updateMovie(id, movieMapper.fromRequestToDto(request));
            return ResponseEntity.ok(updatedMovie);
        } catch (UserNotFoundException e) {
            logger.info("Movie not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a movie by its ID.
     *
     * @param movieId the ID of the movie to delete
     * @return ResponseEntity indicating the result of the deletion attempt
     */
    @DeleteMapping
    public ResponseEntity<MovieDTO> deleteMovie(@RequestParam("movieId") String movieId) {
        try {
            movieService.deleteMovie(movieId);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            logger.info("Movie not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error occurred while deleting movie: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}