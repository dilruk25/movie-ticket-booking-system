package com.dilruk.movieticketbooking.services.movie;

import com.dilruk.movieticketbooking.dtos.MovieDTO;

import java.util.List;

public interface MovieService {

    MovieDTO createMovie(MovieDTO movieDTO);

    List<MovieDTO> getAllMovies();

    MovieDTO getMovieById(String movieId);

    MovieDTO updateMovie(String movieId, MovieDTO movieDTO);

    void deleteMovie(String movieId);

    MovieDTO getMovieByTitle(String title);
}
