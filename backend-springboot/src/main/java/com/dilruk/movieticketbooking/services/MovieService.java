package com.dilruk.movieticketbooking.services;

import com.dilruk.movieticketbooking.dtos.MovieDTO;

import java.util.List;

public interface MovieService {

    MovieDTO createMovie(MovieDTO movieDTO);

    List<MovieDTO> findAllMovies();

    MovieDTO findMovieById(String movieId);

    MovieDTO updateMovie(String movieId, MovieDTO movieDTO);

    void deleteMovie(String movieId);
}
