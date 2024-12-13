package com.dilruk.movieticketbooking.services.user;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.dtos.MovieDTO;
import com.dilruk.movieticketbooking.dtos.UserDTO;
import com.dilruk.movieticketbooking.enums.UserRole;
import com.dilruk.movieticketbooking.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.mappers.UserMapper;
import com.dilruk.movieticketbooking.models.user.User;
import com.dilruk.movieticketbooking.repositories.UserRepository;
import com.dilruk.movieticketbooking.services.movie.MovieServiceImpl;
import com.dilruk.movieticketbooking.services.thread.CustomerThread;
import com.dilruk.movieticketbooking.services.thread.TicketProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Service class handling customer-related functionalities.
 */
@Service
public class CustomerService extends AbstractUserService {

    private final ExecutorService executorService = Executors.newFixedThreadPool(10); // Thread pool for better resource management

    public CustomerService(UserRepository userRepository, MovieServiceImpl movieService, UserMapper userMapper) {
        super(userRepository, userMapper, movieService);
    }

    /**
     * Retrieves all customers from the database.
     *
     * @return List of {@link UserDTO} representing customers.
     */
    @Override
    public List<UserDTO> getAllUsers() {
        List<User> customers = userRepository.findUsersByRole(UserRole.ROLE_CUSTOMER);
        return customers.stream().map(userMapper::fromEntityToDto).toList();
    }

    /**
     * Retrieves a customer by their user ID.
     *
     * @param userId The user ID of the customer.
     * @return {@link UserDTO} representing the retrieved customer.
     * @throws UserNotFoundException If the customer not found with the provided user ID.
     */
    @Override
    public UserDTO getUserByUserId(String userId) {
        User existCustomer = userRepository.findUserByRoleAndUserId(UserRole.ROLE_CUSTOMER, userId)
                .orElseThrow(() -> new UserNotFoundException("Customer not found with the id: " + userId));

        return userMapper.fromEntityToDto(existCustomer);
    }

    /**
     * Updates an existing customer's information.
     *
     * @param userId  The user ID of the customer to update.
     * @param userDTO The updated customer information.
     * @return {@link UserDTO} representing the updated customer.
     * @throws UserNotFoundException If the customer not found with the provided user ID.
     */
    @Override
    public UserDTO updateUser(String userId, UserDTO userDTO) {
        User existingCustomer = userRepository.findUserByRoleAndUserId(UserRole.ROLE_CUSTOMER, userId)
                .orElseThrow(() -> new UserNotFoundException("Customer not found with the id: " + userId));

        existingCustomer.setName(userDTO.getName());
        existingCustomer.setEmail(userDTO.getEmail());
        existingCustomer.setPassword(userDTO.getPassword());
        existingCustomer.setRole(userDTO.getRole());

        User updatedCustomer = userRepository.save(existingCustomer);

        logger.info("Customer updated successfully: {}", userId);
        return userMapper.fromEntityToDto(updatedCustomer);
    }

    /**
     * Deletes a customer from the database.
     *
     * @param userId The user ID of the customer to delete.
     * @throws UserNotFoundException If the customer not found with the provided user ID.
     */
    @Override
    public void deleteUser(String userId) {
        User existCustomer = userRepository.findUserByRoleAndUserId(UserRole.ROLE_CUSTOMER, userId)
                .orElseThrow(() -> new UserNotFoundException("Customer not found with the id: " + userId));

        userRepository.delete(existCustomer);
        logger.info("Customer deleted successfully: {}", userId);
    }

    /**
     * Performs an action for the customer by starting a new thread to process ticket actions.
     * <p>
     * Uses a thread pool for better thread management and resource usage.
     *
     * @param systemConfig    The system configuration containing settings relevant to the customer.
     * @param ticketProcessor The ticket processor used by the customer thread.
     */
    @Override
    public void performAction(SystemConfig systemConfig, TicketProcessor ticketProcessor) {
        executorService.submit(new CustomerThread(systemConfig, ticketProcessor));  // Submit task to thread pool
        logger.info("Customer action initiated: Ticket processing started.");
    }

    /**
     * Retrieves all movies available in the system.
     *
     * @return List of {@link MovieDTO} representing the available movies.
     */
    public List<MovieDTO> getAllMovies() {
        return movieService.getAllMovies();
    }
}