package com.dilruk.movieticketbooking.services.user;

import com.dilruk.movieticketbooking.dtos.EventDTO;
import com.dilruk.movieticketbooking.dtos.MovieDTO;
import com.dilruk.movieticketbooking.dtos.UserDTO;
import com.dilruk.movieticketbooking.exceptions.DuplicateDataException;
import com.dilruk.movieticketbooking.mappers.UserMapper;
import com.dilruk.movieticketbooking.models.User;
import com.dilruk.movieticketbooking.repositories.UserRepository;
import com.dilruk.movieticketbooking.services.MovieServiceImpl;
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

    // To allow access to the subclass
    protected final UserRepository userRepository;
    protected final MovieServiceImpl movieService;
    protected final EventServiceImpl eventService;
    protected final UserMapper userMapper;

    // Implemented method
    public UserDTO createUser(UserDTO userDTO) {
        Optional<User> existUser = userRepository.findUserByEmail(userDTO.getEmail());
        if (existUser.isPresent()) {
            throw new DuplicateDataException("User email already exists");
        }
        User savedUser = userRepository.save(userMapper.fromDtoToEntity(userDTO));
        return userMapper.fromEntityToDto(savedUser);
    }

    public List<MovieDTO> findAllMovies() {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    public List<EventDTO> findAllEvents() {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    public List<UserDTO> findAllCustomers() {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    public List<UserDTO> findAllVendors() {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    public UserDTO findUserByUserId(String userId) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    public UserDTO updateUser(String userId, UserDTO userDTO) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    public void deleteUser(String userId) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    public boolean requestAddMovie(MovieDTO movieDTO, boolean approved) {
        throw new UnsupportedOperationException("Unsupported operation.");
    }

    public List<EventDTO> findEventsByVendorId(String userId) {
        throw new UnsupportedOperationException("Unsupported operation");
    }
}
