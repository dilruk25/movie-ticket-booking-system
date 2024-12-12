package com.dilruk.movieticketbooking.services.user;

import com.dilruk.movieticketbooking.dtos.UserDTO;
import com.dilruk.movieticketbooking.exceptions.UserAlreadyExistsException;
import com.dilruk.movieticketbooking.mappers.UserMapper;
import com.dilruk.movieticketbooking.models.user.User;
import com.dilruk.movieticketbooking.repositories.UserRepository;
import com.dilruk.movieticketbooking.services.movie.MovieServiceImpl;
import com.dilruk.movieticketbooking.services.event.EventServiceImpl;
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
public abstract class AbstractUserService {

    // Make protected To allow access to the subclass
    protected final UserRepository userRepository;
    protected final UserMapper userMapper;
    protected final MovieServiceImpl movieService;
    protected final EventServiceImpl eventService;


    // Implemented method
    public UserDTO createUser(UserDTO userDTO) {
        Optional<User> existUser = userRepository.findUserByEmail(userDTO.getEmail());
        if (existUser.isPresent()) {
            throw new UserAlreadyExistsException("User email already exists");
        }
        User savedUser = userRepository.save(userMapper.fromDtoToEntity(userDTO));
        return userMapper.fromEntityToDto(savedUser);
    }

    public abstract List<UserDTO> getAllUsers();

    public abstract UserDTO getUserByUserId(String userId);

    public abstract UserDTO updateUser(String userId, UserDTO userDTO);

    public abstract void deleteUser(String userId);
}
