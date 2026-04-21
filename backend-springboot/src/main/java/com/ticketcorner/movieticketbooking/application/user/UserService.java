package com.ticketcorner.movieticketbooking.application.user;

import com.ticketcorner.movieticketbooking.dto.UserDTO;
import com.ticketcorner.movieticketbooking.common.exceptions.UserAlreadyExistsException;
import com.ticketcorner.movieticketbooking.common.mappers.UserMapper;
import com.ticketcorner.movieticketbooking.domain.user.entity.User;
import com.ticketcorner.movieticketbooking.domain.user.repository.UserRepository;
import com.ticketcorner.movieticketbooking.application.movie.MovieServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Abstract service class for managing users.
 * Provides common functionality for user creation, retrieval, and management.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    // Protected to allow access to subclasses
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected final UserRepository userRepository;
    protected final UserMapper userMapper;
    protected final MovieServiceImpl movieService;

    public UserDTO createUser(UserDTO userDTO) {
        Optional<User> existUser = userRepository.findUserByEmail(userDTO.getEmail());
        if (existUser.isPresent()) {
            throw new UserAlreadyExistsException("User email already exists");
        }
        User savedUser = userRepository.save(userMapper.fromDtoToEntity(userDTO));
        return userMapper.fromEntityToDto(savedUser);
    }
}