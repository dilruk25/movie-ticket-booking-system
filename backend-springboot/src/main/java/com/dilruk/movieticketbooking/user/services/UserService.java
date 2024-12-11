package com.dilruk.movieticketbooking.user.services;

import com.dilruk.movieticketbooking.common.exceptions.DuplicateDataException;
import com.dilruk.movieticketbooking.common.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.common.mappers.UserMapper;
import com.dilruk.movieticketbooking.movie.dtos.EventDTO;
import com.dilruk.movieticketbooking.movie.dtos.MovieDTO;
import com.dilruk.movieticketbooking.movie.services.EventServiceImpl;
import com.dilruk.movieticketbooking.movie.services.MovieServiceImpl;
import com.dilruk.movieticketbooking.user.dtos.UserDTO;
import com.dilruk.movieticketbooking.user.models.User;
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
public abstract class UserService {

    private final UserRepository userRepository;
    private final MovieServiceImpl movieService;
    private final EventServiceImpl eventService;
    private final UserMapper userMapper;


    public UserDTO createUser(UserDTO userDTO) {
        Optional<User> existUser = userRepository.findUserByEmail(userDTO.getEmail());
        if (existUser.isPresent()) {
            throw new DuplicateDataException("User email already exists");
        }

        User savedUser = userRepository.save(userMapper.fromDtoToEntity(userDTO));
        return userMapper.fromEntityToDto(savedUser);
    }

    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::fromEntityToDto).toList();
    }

    public UserDTO findUserById(String userId) {
        User existUser = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with the id: " + userId));

        return userMapper.fromEntityToDto(existUser);
    }

    public UserDTO updateUser(String userId, UserDTO userDTO) {
        User existingUser = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with the id: " + userId));

        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPassword(userDTO.getPassword());
        existingUser.setRole(userDTO.getRole());

        User updatedUser = userRepository.save(existingUser);

        return userMapper.fromEntityToDto(updatedUser);
    }

    public void deleteUser(String userId) {
        User existUser = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with the id: " + userId));

        userRepository.delete(existUser);
    }

    public List<MovieDTO> findAllMovies() {
        return movieService.findAllMovies();
    }

    public List<EventDTO> findAllEvents() {
        return eventService.findAllEvents();
    }
}
