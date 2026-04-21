package com.ticketcorner.movieticketbooking.application.user;

import com.ticketcorner.movieticketbooking.configuration.SystemConfig;
import com.ticketcorner.movieticketbooking.dto.MovieDTO;
import com.ticketcorner.movieticketbooking.dto.UserDTO;
import com.ticketcorner.movieticketbooking.common.enums.UserRoleEnum;
import com.ticketcorner.movieticketbooking.common.exceptions.UserNotFoundException;
import com.ticketcorner.movieticketbooking.common.mappers.UserMapper;
import com.ticketcorner.movieticketbooking.domain.user.entity.User;
import com.ticketcorner.movieticketbooking.domain.user.repository.UserRepository;
import com.ticketcorner.movieticketbooking.application.movie.MovieServiceImpl;
import com.ticketcorner.movieticketbooking.application.thread.CustomerThread;
import com.ticketcorner.movieticketbooking.application.thread.TicketProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Service class handling customer-related functionalities.
 */
@Service
public class CustomerService extends UserService {

    private final ExecutorService executorService = Executors.newFixedThreadPool(10); // Thread pool for better resource management

    public CustomerService(UserRepository userRepository, MovieServiceImpl movieService, UserMapper userMapper) {
        super(userRepository, userMapper, movieService);
    }

    /**
     * Retrieves all customers from the database.
     *
     * @return List of {@link UserDTO} representing customers.
     */
    public List<UserDTO> getAllUsers() {
        List<User> customers = userRepository.findUsersByRole(UserRoleEnum.ROLE_CUSTOMER);
        return customers.stream().map(userMapper::fromEntityToDto).toList();
    }

    /**
     * Retrieves a customer by their user ID.
     *
     * @param userId The user ID of the customer.
     * @return {@link UserDTO} representing the retrieved customer.
     * @throws UserNotFoundException If the customer not found with the provided user ID.
     */
    public UserDTO getCustomerByCustomerId(String userId) {
        User existCustomer = userRepository.findUserByRoleAndUserId(UserRoleEnum.ROLE_CUSTOMER, userId).orElseThrow(() -> new UserNotFoundException("Customer not found with the id: " + userId));

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
    public UserDTO updateCustomer(String userId, UserDTO userDTO) {
        User existingCustomer = userRepository.findUserByRoleAndUserId(UserRoleEnum.ROLE_CUSTOMER, userId).orElseThrow(() -> new UserNotFoundException("Customer not found with the id: " + userId));

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
    public void deleteCustomer(String userId) {
        User existCustomer = userRepository.findUserByRoleAndUserId(UserRoleEnum.ROLE_CUSTOMER, userId).orElseThrow(() -> new UserNotFoundException("Customer not found with the id: " + userId));

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