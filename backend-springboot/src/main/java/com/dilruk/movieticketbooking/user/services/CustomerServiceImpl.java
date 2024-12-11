package com.dilruk.movieticketbooking.user.services;

import com.dilruk.movieticketbooking.common.mappers.UserMapper;
import com.dilruk.movieticketbooking.movie.services.EventServiceImpl;
import com.dilruk.movieticketbooking.movie.services.MovieServiceImpl;
import com.dilruk.movieticketbooking.user.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
public class CustomerServiceImpl extends UserService {

    public CustomerServiceImpl(UserRepository userRepository, MovieServiceImpl movieService, EventServiceImpl eventService, UserMapper userMapper) {
        super(userRepository, movieService, eventService, userMapper);
    }



}
