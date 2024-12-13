package com.dilruk.movieticketbooking.services.user;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.dtos.UserDTO;
import com.dilruk.movieticketbooking.exceptions.UserAlreadyExistsException;
import com.dilruk.movieticketbooking.mappers.UserMapper;
import com.dilruk.movieticketbooking.models.user.User;
import com.dilruk.movieticketbooking.repositories.UserRepository;
import com.dilruk.movieticketbooking.services.movie.MovieServiceImpl;
import com.dilruk.movieticketbooking.services.thread.TicketProcessor;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Abstract service class for managing users.
 * Provides common functionality for user creation, retrieval, and management.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public abstract class AbstractUserService {

    // Protected to allow access to subclasses
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected final UserRepository userRepository;
    protected final UserMapper userMapper;
    protected final MovieServiceImpl movieService;

    /**
     * Creates a new user in the system. Throws an exception if the user already exists.
     *
     * @param userDTO The user data transfer object containing the details for the new user.
     * @return The created user DTO.
     * @throws UserAlreadyExistsException If a user with the given email already exists.
     */
    public UserDTO createUser(UserDTO userDTO) {
        // Check if the user already exists by email
        Optional<User> existingUser = userRepository.findUserByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            logger.error("Attempted to create user with existing email: {}", userDTO.getEmail());
            throw new UserAlreadyExistsException("User with email " + userDTO.getEmail() + " already exists");
        }

        try {
            // Save the new user
            User savedUser = userRepository.save(userMapper.fromDtoToEntity(userDTO));
            logger.info("User created successfully: {}", userDTO.getEmail());
            return userMapper.fromEntityToDto(savedUser);
        } catch (Exception e) {
            logger.error("Error occurred while creating user with email: {}", userDTO.getEmail(), e);
            throw new RuntimeException("Error creating user", e);
        }
    }

    /**
     * Abstract method to retrieve all users.
     * Implemented by subclasses like VendorService and CustomerService.
     *
     * @return List of all user DTOs.
     */
    public abstract List<UserDTO> getAllUsers();

    /**
     * Abstract method to retrieve a user by user ID.
     * Implemented by subclasses.
     *
     * @param userId The ID of the user.
     * @return The user DTO.
     */
    public abstract UserDTO getUserByUserId(String userId);

    /**
     * Abstract method to update a user.
     * Implemented by subclasses.
     *
     * @param userId  The ID of the user to update.
     * @param userDTO The new user data.
     * @return The updated user DTO.
     */
    public abstract UserDTO updateUser(String userId, UserDTO userDTO);

    /**
     * Abstract method to delete a user by user ID.
     * Implemented by subclasses.
     *
     * @param userId The ID of the user to delete.
     */
    public abstract void deleteUser(String userId);

    /**
     * Performs actions related to ticket processing (e.g., adding or buying tickets).
     * Currently not implemented for admin users.
     *
     * @param systemConfig Configuration details for system behavior.
     * @param ticketProcessor Ticket processor to handle ticket operations.
     * @throws UnsupportedOperationException if this method is called directly on AbstractUserService.
     */
    public void performAction(SystemConfig systemConfig, TicketProcessor ticketProcessor) {
        // This operation is not supported by the base class and should be implemented in vendor and customer class.
        throw new UnsupportedOperationException("This operation is not supported for this user type.");
    }
}