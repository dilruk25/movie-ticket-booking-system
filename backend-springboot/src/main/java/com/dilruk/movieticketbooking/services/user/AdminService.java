package com.dilruk.movieticketbooking.services.user;

import com.dilruk.movieticketbooking.api.request.ConfigRequest;
import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.dtos.UserDTO;
import com.dilruk.movieticketbooking.enums.UserRole;
import com.dilruk.movieticketbooking.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.mappers.UserMapper;
import com.dilruk.movieticketbooking.models.user.User;
import com.dilruk.movieticketbooking.repositories.UserRepository;
import com.dilruk.movieticketbooking.services.movie.MovieServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class handling admin-related functionalities, including system configuration and user management.
 */
@Service
public class AdminService extends AbstractUserService {

    private SystemConfig systemConfig;

    public AdminService(UserRepository userRepository, MovieServiceImpl movieService, UserMapper userMapper) {
        super(userRepository, userMapper, movieService);
    }

    /**
     * Configures the system based on the provided configuration request.
     *
     * @param configRequest The configuration details for the system.
     * @throws IllegalArgumentException If any of the configuration values are invalid (e.g., negative numbers).
     */
    public void configureSystem(ConfigRequest configRequest) {
        // Validation for system configuration values
        if (configRequest.getTotalTickets() < 0 || configRequest.getMaxTicketCapacity() < 0 || configRequest.getTicketReleaseRate() < 0 || configRequest.getCustomerRetrievalRate() < 0) {
            throw new IllegalArgumentException("System configuration values must be non-negative.");
        }

        this.systemConfig = new SystemConfig();
        systemConfig.setTotalTickets(configRequest.getTotalTickets());
        systemConfig.setMaxTicketCapacity(configRequest.getMaxTicketCapacity());
        systemConfig.setTicketReleaseRate(configRequest.getTicketReleaseRate());
        systemConfig.setCustomerRetrievalRate(configRequest.getCustomerRetrievalRate());

        logger.info("System configured: Total Tickets: {}, Max Ticket Capacity: {}, Ticket Release Rate: {}, Customer Retrieval Rate: {}",
                configRequest.getTotalTickets(), configRequest.getMaxTicketCapacity(), configRequest.getTicketReleaseRate(), configRequest.getCustomerRetrievalRate());
    }

    /**
     * Retrieves all admins from the database.
     *
     * @return List of {@link UserDTO} representing admins.
     */
    @Override
    public List<UserDTO> getAllUsers() {
        List<User> admins = userRepository.findUsersByRole(UserRole.ROLE_ADMIN);
        return admins.stream().map(userMapper::fromEntityToDto).toList();
    }

    /**
     * Retrieves an admin by their user ID.
     *
     * @param userId The user ID of the admin.
     * @return {@link UserDTO} representing the retrieved admin.
     * @throws UserNotFoundException If admin not found with the provided user ID.
     */
    @Override
    public UserDTO getUserByUserId(String userId) {
        User existAdmin = userRepository.findUserByRoleAndUserId(UserRole.ROLE_ADMIN, userId)
                .orElseThrow(() -> new UserNotFoundException("Admin not found with the id: " + userId));

        return userMapper.fromEntityToDto(existAdmin);
    }

    /**
     * Updates an existing admin's information.
     *
     * @param userId  The user ID of the admin to update.
     * @param userDTO The updated admin information.
     * @return {@link UserDTO} representing the updated admin.
     * @throws UserNotFoundException If admin not found with the provided user ID.
     */
    @Override
    public UserDTO updateUser(String userId, UserDTO userDTO) {
        User existingAdmin = userRepository.findUserByRoleAndUserId(UserRole.ROLE_ADMIN, userId)
                .orElseThrow(() -> new UserNotFoundException("Admin not found with the id: " + userId));

        existingAdmin.setName(userDTO.getName());
        existingAdmin.setEmail(userDTO.getEmail());
        existingAdmin.setPassword(userDTO.getPassword());
        existingAdmin.setRole(userDTO.getRole());

        User updatedAdmin = userRepository.save(existingAdmin);

        logger.info("Admin updated successfully: {}", userId);
        return userMapper.fromEntityToDto(updatedAdmin);
    }

    /**
     * Deletes an admin from the database.
     *
     * @param userId The user ID of the admin to delete.
     * @throws UserNotFoundException If admin not found with the provided user ID.
     */
    @Override
    public void deleteUser(String userId) {
        User existAdmin = userRepository.findUserByRoleAndUserId(UserRole.ROLE_ADMIN, userId)
                .orElseThrow(() -> new UserNotFoundException("Admin not found with the id: " + userId));

        userRepository.delete(existAdmin);
        logger.info("Admin deleted successfully: {}", userId);
    }

    /**
     * Retrieves the current system configuration.
     *
     * @return The current {@link SystemConfig}.
     * @throws IllegalStateException If the system has not been configured yet.
     */
    public SystemConfig getConfiguration() {
        if (this.systemConfig == null) {
            throw new IllegalStateException("System configuration has not been set. Please configure the system first.");
        }
        return this.systemConfig;
    }
}