package com.dilruk.movieticketbooking.movie.services;

import com.dilruk.movieticketbooking.movie.dtos.MovieDTO;

import java.util.List;

public interface MovieService {

    MovieDTO createMovie(MovieDTO movieDTO);

    List<MovieDTO> getAllMovies();

    MovieDTO getMovieById(String movieId);

    MovieDTO updateMovie(String movieId, MovieDTO movieDTO);

    void deleteMovie(String movieId);
}
