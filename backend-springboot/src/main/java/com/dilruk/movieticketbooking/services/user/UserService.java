package com.dilruk.movieticketbooking.services.user;

import com.dilruk.movieticketbooking.dtos.UserDTO;
import com.dilruk.movieticketbooking.exceptions.UserAlreadyExistsException;
import com.dilruk.movieticketbooking.mappers.UserMapper;
import com.dilruk.movieticketbooking.models.user.User;
import com.dilruk.movieticketbooking.repositories.UserRepository;
import com.dilruk.movieticketbooking.services.movie.MovieServiceImpl;
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