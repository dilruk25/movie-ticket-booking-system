package com.dilruk.movieticketbooking.services.movie;

import com.dilruk.movieticketbooking.exceptions.MovieAlreadyExistsException;
import com.dilruk.movieticketbooking.exceptions.MovieNotFoundException;
import com.dilruk.movieticketbooking.mappers.MovieMapper;
import com.dilruk.movieticketbooking.dtos.MovieDTO;
import com.dilruk.movieticketbooking.models.movie.Movie;
import com.dilruk.movieticketbooking.repositories.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the MovieService interface that provides methods for managing movies.
 * It allows creating, updating, retrieving, and deleting movies, as well as handling exceptions
 * when the movie already exists or is not found.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;  // Repository for interacting with the movie database
    private final MovieMapper movieMapper;  // Mapper for converting between MovieDTO and Movie entities

    /**
     * Creates a new movie entry in the database if a movie with the same title and year does not already exist.
     *
     * @param movieDTO The movie data transfer object containing the movie details to be created.
     * @return The created MovieDTO.
     * @throws MovieAlreadyExistsException If a movie with the same title and year already exists in the database.
     */
    @Override
    public MovieDTO createMovie(MovieDTO movieDTO) {
        // Check if the movie with the same title and year already exists
        Optional<Movie> existMovie = movieRepository.findMovieByTitleAndYear(movieDTO.getTitle(), movieDTO.getYear());
        if (existMovie.isPresent()) {
            throw new MovieAlreadyExistsException("Movie already exists");
        }

        // Save the new movie and return its DTO representation
        Movie savedMovie = movieRepository.save(movieMapper.fromDtoToEntity(movieDTO));
        return movieMapper.fromEntityToDto(savedMovie);
    }

    /**
     * Retrieves all movies from the database.
     *
     * @return A list of MovieDTOs representing all the movies in the database.
     */
    @Override
    public List<MovieDTO> getAllMovies() {
        // Fetch all movies and map them to DTOs
        List<Movie> movies = movieRepository.findAll();
        return movies.stream().map(movieMapper::fromEntityToDto).toList();
    }

    /**
     * Retrieves a movie by its unique ID.
     *
     * @param movieId The unique identifier of the movie to be retrieved.
     * @return The MovieDTO of the found movie.
     * @throws MovieNotFoundException If no movie with the specified ID is found.
     */
    @Override
    public MovieDTO getMovieById(String movieId) {
        // Look for the movie by ID and throw an exception if not found
        Movie existMovie = movieRepository.findMovieByMovieId(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with the id: " + movieId));

        return movieMapper.fromEntityToDto(existMovie);
    }

    /**
     * Retrieves a movie by its title.
     *
     * @param title The title of the movie to be retrieved.
     * @return The MovieDTO of the found movie.
     * @throws MovieNotFoundException If no movie with the specified title is found.
     */
    public MovieDTO getMovieByTitle(String title) {
        // Look for the movie by title and throw an exception if not found
        Movie existMovie = movieRepository.findMovieByTitle(title)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with the title: " + title));

        return movieMapper.fromEntityToDto(existMovie);
    }

    /**
     * Updates the details of an existing movie.
     *
     * @param movieId The ID of the movie to be updated.
     * @param movieDTO The new details for the movie.
     * @return The updated MovieDTO.
     * @throws MovieNotFoundException If the movie with the specified ID is not found.
     */
    @Override
    public MovieDTO updateMovie(String movieId, MovieDTO movieDTO) {
        // Retrieve the movie and update its details
        Movie existingMovie = movieRepository.findMovieByMovieId(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with the id: " + movieId));

        // Update fields with new values from movieDTO
        existingMovie.setTitle(movieDTO.getTitle());
        existingMovie.setDuration(movieDTO.getDuration());
        existingMovie.setGenre(movieDTO.getGenre());
        existingMovie.setRating(movieDTO.getRating());

        // Save the updated movie and return its DTO representation
        Movie updatedMovie = movieRepository.save(existingMovie);
        return movieMapper.fromEntityToDto(updatedMovie);
    }

    /**
     * Deletes a movie from the database.
     *
     * @param movieId The ID of the movie to be deleted.
     * @throws MovieNotFoundException If no movie with the specified ID is found.
     */
    @Override
    public void deleteMovie(String movieId) {
        // Retrieve the movie to be deleted and throw an exception if not found
        Movie existMovie = movieRepository.findMovieByMovieId(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with the id: " + movieId));

        // Delete the movie from the database
        movieRepository.delete(existMovie);
    }
}