package com.dilruk.movieticketbooking.api;

import com.dilruk.movieticketbooking.exceptions.DuplicateDataException;
import com.dilruk.movieticketbooking.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.mappers.MovieMapper;
import com.dilruk.movieticketbooking.api.request.MovieRequest;
import com.dilruk.movieticketbooking.dtos.MovieDTO;
import com.dilruk.movieticketbooking.services.MovieServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieServiceImpl movieService;
    private final MovieMapper movieMapper;


    @PostMapping
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieRequest movie) {
        try {
            MovieDTO savedMovie = movieService.createMovie(movieMapper.fromRequestToDto(movie));
            return ResponseEntity.ok(savedMovie);
        } catch (DuplicateDataException e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        List<MovieDTO> movies = movieService.findAllMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("{id}")
    public ResponseEntity<MovieDTO> getMovie(@PathVariable String id) {
        try {
            MovieDTO movie = movieService.findMovieById(id);
            return ResponseEntity.ok(movie);

        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable String id, @RequestBody MovieRequest request) {
        try {
            MovieDTO updatedMovie = movieService.updateMovie(id, movieMapper.fromRequestToDto(request));
            return ResponseEntity.ok(updatedMovie);
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<MovieDTO> deleteMovie(@RequestParam("movieId") String movieId) {
        try {
            movieService.deleteMovie(movieId);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
