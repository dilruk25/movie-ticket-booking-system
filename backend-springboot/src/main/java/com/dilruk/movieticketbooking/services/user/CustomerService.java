package com.dilruk.movieticketbooking.services.user;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.dtos.MovieDTO;
import com.dilruk.movieticketbooking.dtos.UserDTO;
import com.dilruk.movieticketbooking.enums.UserRole;
import com.dilruk.movieticketbooking.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.mappers.UserMapper;
import com.dilruk.movieticketbooking.models.TicketPool;
import com.dilruk.movieticketbooking.models.user.User;
import com.dilruk.movieticketbooking.repositories.UserRepository;
import com.dilruk.movieticketbooking.services.movie.MovieServiceImpl;
import com.dilruk.movieticketbooking.services.event.EventServiceImpl;
import com.dilruk.movieticketbooking.services.thread.CustomerThread;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class handling customer related functionalities.
 */
@Service
public class CustomerService extends AbstractUserService {

    public CustomerService(UserRepository userRepository, MovieServiceImpl movieService, EventServiceImpl eventService, UserMapper userMapper) {
        super(userRepository, userMapper, movieService, eventService);
    }

    /**
     * Retrieves all customers from the database.
     *
     * @return List of {@link UserDTO} representing customers.
     */
    @Override
    public List<UserDTO> getAllUsers() {
        List<User> customers = userRepository.findUsersByRole(UserRole.CUSTOMER);
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
        User existCustomer = userRepository.findUserByRoleAndUserId(UserRole.CUSTOMER, userId)
                .orElseThrow(() -> new UserNotFoundException("Customer not found with the id: " + userId));

        return userMapper.fromEntityToDto(existCustomer);
    }

    /**
     * Updates an existing customer's information.
     *
     * @param userId The user ID of the customer to update.
     * @param userDTO The updated customer information.
     * @return {@link UserDTO} representing the updated customer.
     * @throws UserNotFoundException If the customer not found with the provided user ID.
     */
    @Override
    public UserDTO updateUser(String userId, UserDTO userDTO) {
        User existingCustomer = userRepository.findUserByRoleAndUserId(UserRole.CUSTOMER, userId)
                .orElseThrow(() -> new UserNotFoundException("Customer not found with the id: " + userId));

        existingCustomer.setName(userDTO.getName());
        existingCustomer.setEmail(userDTO.getEmail());
        existingCustomer.setPassword(userDTO.getPassword());
        existingCustomer.setRole(userDTO.getRole());

        User updatedCustomer = userRepository.save(existingCustomer);

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
        User existCustomer = userRepository.findUserByRoleAndUserId(UserRole.CUSTOMER, userId)
                .orElseThrow(() -> new UserNotFoundException("Customer not found with the id: " + userId));

        userRepository.delete(existCustomer);
    }

    /**
     * Overrides the base method to perform a customer specific action.
     *
     * This implementation creates a new `CustomerThread` instance with the provided configuration and ticket pool.
     *
     * @param systemConfig The system configuration object containing relevant settings for the customer.
     * @param ticketPool The shared ticket pool used for ticket purchases.
     */
    @Override
    public void performAction(SystemConfig systemConfig, TicketPool ticketPool) {
        new CustomerThread(systemConfig, ticketPool).start();
        logger.info("Customer thread started");
    }

    public List<MovieDTO> getAllMovies() {
        return movieService.getAllMovies();
    }
}
