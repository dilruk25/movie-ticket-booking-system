package com.dilruk.movieticketbooking.user.services;

import com.dilruk.movieticketbooking.common.exceptions.DuplicateDataException;
import com.dilruk.movieticketbooking.common.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.common.mappers.UserMapper;
import com.dilruk.movieticketbooking.movie.services.EventServiceImpl;
import com.dilruk.movieticketbooking.movie.services.MovieServiceImpl;
import com.dilruk.movieticketbooking.user.repositories.UserRepository;
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
public class VendorServiceImpl extends UserService {



    public VendorServiceImpl(UserRepository userRepository, MovieServiceImpl movieService, EventServiceImpl eventService, UserMapper userMapper) {
        super(userRepository, movieService, eventService, userMapper);
    }

}
