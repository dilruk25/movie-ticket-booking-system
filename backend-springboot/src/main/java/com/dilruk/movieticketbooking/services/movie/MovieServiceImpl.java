package com.dilruk.movieticketbooking.services.movie;

import com.dilruk.movieticketbooking.exceptions.MovieAlreadyExistsException;
import com.dilruk.movieticketbooking.exceptions.MovieNotFoundException;
import com.dilruk.movieticketbooking.mappers.MovieMapper;
import com.dilruk.movieticketbooking.dtos.MovieDTO;
import com.dilruk.movieticketbooking.models.Movie;
import com.dilruk.movieticketbooking.repositories.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;


    @Override
    public MovieDTO createMovie(MovieDTO movieDTO) {
        Optional<Movie> existMovie = movieRepository.findMovieByTitle(movieDTO.getTitle());
        if (existMovie.isPresent()) {
            throw new MovieAlreadyExistsException("Movie email already exists");
        }

        Movie savedMovie = movieRepository.save(movieMapper.fromDtoToEntity(movieDTO));
        return movieMapper.fromEntityToDto(savedMovie);
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream().map(movieMapper::fromEntityToDto).toList();
    }

    @Override
    public MovieDTO getMovieById(String movieId) {
        Movie existMovie = movieRepository.findMovieByMovieId(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with the id: " + movieId));

        return movieMapper.fromEntityToDto(existMovie);
    }

    @Override
    public MovieDTO updateMovie(String movieId, MovieDTO movieDTO) {
        Movie existingMovie = movieRepository.findMovieByMovieId(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with the id: " + movieId));

        existingMovie.setTitle(movieDTO.getTitle());
        existingMovie.setDuration(movieDTO.getDuration());
        existingMovie.setGenre(movieDTO.getGenre());
        existingMovie.setRating(movieDTO.getRating());

        Movie updatedMovie = movieRepository.save(existingMovie);

        return movieMapper.fromEntityToDto(updatedMovie);
    }

    @Override
    public void deleteMovie(String movieId) {
        Movie existMovie = movieRepository.findMovieByMovieId(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with the id: " + movieId));

        movieRepository.delete(existMovie);
    }

    public MovieDTO getMovieByTitle(String title) {
        Movie existMovie = movieRepository.findMovieByTitle(title).orElseThrow(() -> new MovieNotFoundException("Movie not found with the title: " + title));

        return movieMapper.fromEntityToDto(existMovie);
    }
}
