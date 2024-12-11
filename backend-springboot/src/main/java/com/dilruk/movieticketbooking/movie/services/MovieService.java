package com.dilruk.movieticketbooking.movie.services;

import com.dilruk.movieticketbooking.movie.dtos.MovieDTO;

import java.util.List;

public interface MovieService {

    MovieDTO createMovie(MovieDTO movieDTO);

    List<MovieDTO> findAllMovies();

    MovieDTO findMovieById(String movieId);

    MovieDTO updateMovie(String movieId, MovieDTO movieDTO);

    void deleteMovie(String movieId);
}
