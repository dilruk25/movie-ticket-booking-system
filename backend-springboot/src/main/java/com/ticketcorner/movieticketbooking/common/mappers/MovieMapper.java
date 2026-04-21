package com.ticketcorner.movieticketbooking.common.mappers;

import com.ticketcorner.movieticketbooking.api.v1.ro.request.MovieRequest;
import com.ticketcorner.movieticketbooking.dto.MovieDTO;
import com.ticketcorner.movieticketbooking.common.enums.IdPrefixEnum;
import com.ticketcorner.movieticketbooking.domain.movie.entity.Movie;
import com.ticketcorner.movieticketbooking.common.utils.IdGenerator;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public MovieDTO fromRequestToDto(MovieRequest request) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setTitle(request.getTitle());
        movieDTO.setDuration(request.getDuration());
        movieDTO.setGenre(request.getGenre());
        movieDTO.setRating(request.getRating());
        movieDTO.setYear(request.getYear());

        return movieDTO;
    }

    public Movie fromDtoToEntity(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setMovieId(IdGenerator.generateId(IdPrefixEnum.MOVIE_PREFIX.getPrefix()));
        movie.setTitle(movieDTO.getTitle());
        movie.setDuration(movieDTO.getDuration());
        movie.setGenre(movieDTO.getGenre());
        movie.setRating(movieDTO.getRating());
        movie.setYear(movieDTO.getYear());

        return movie;
    }

    public MovieDTO fromEntityToDto(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setMovieId(movie.getMovieId());
        movieDTO.setTitle(movie.getTitle());
        movieDTO.setDuration(movie.getDuration());
        movieDTO.setGenre(movie.getGenre());
        movieDTO.setRating(movie.getRating());
        movieDTO.setYear(movie.getYear());

        return movieDTO;
    }
}