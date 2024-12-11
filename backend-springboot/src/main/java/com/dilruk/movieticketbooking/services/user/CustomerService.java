package com.dilruk.movieticketbooking.services.user;

import com.dilruk.movieticketbooking.dtos.MovieDTO;
import com.dilruk.movieticketbooking.mappers.UserMapper;
import com.dilruk.movieticketbooking.repositories.UserRepository;
import com.dilruk.movieticketbooking.services.MovieServiceImpl;
import com.dilruk.movieticketbooking.services.event.EventServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService extends AbstractUserService {

    public CustomerService(UserRepository userRepository, MovieServiceImpl movieService, EventServiceImpl eventService, UserMapper userMapper) {
        super(userRepository, movieService, eventService, userMapper);
    }

    @Override
    public List<MovieDTO> findAllMovies() {
        return movieService.findAllMovies();
    }


}
