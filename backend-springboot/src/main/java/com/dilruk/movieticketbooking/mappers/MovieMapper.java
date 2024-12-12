package com.dilruk.movieticketbooking.mappers;

import com.dilruk.movieticketbooking.api.request.MovieRequest;
import com.dilruk.movieticketbooking.api.response.MovieResponse;
import com.dilruk.movieticketbooking.dtos.MovieDTO;
import com.dilruk.movieticketbooking.models.movie.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public MovieDTO fromRequestToDto(MovieRequest request) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setTitle(request.getTitle());
        movieDTO.setDuration(request.getDuration());
        movieDTO.setGenre(request.getGenre());
        movieDTO.setRating(request.getRating());

        return movieDTO;
    }

    public Movie fromDtoToEntity(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setMovieId(movieDTO.getMovieId());
        movie.setTitle(movieDTO.getTitle());
        movie.setDuration(movieDTO.getDuration());
        movie.setGenre(movieDTO.getGenre());
        movie.setRating(movieDTO.getRating());

        return movie;
    }

    public MovieDTO fromEntityToDto(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setMovieId(movie.getMovieId());
        movieDTO.setTitle(movie.getTitle());
        movieDTO.setDuration(movie.getDuration());
        movieDTO.setGenre(movie.getGenre());
        movieDTO.setRating(movie.getRating());

        return movieDTO;
    }

    public MovieResponse fromDtoToResponse(MovieDTO movieDTO) {
        MovieResponse response = new MovieResponse();
        response.setMovieId(movieDTO.getMovieId());
        response.setTitle(movieDTO.getTitle());
        response.setDuration(movieDTO.getDuration());
        response.setGenre(movieDTO.getGenre());
        response.setRating(movieDTO.getRating());

        return response;
    }
}