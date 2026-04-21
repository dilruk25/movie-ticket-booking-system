package com.ticketcorner.movieticketbooking.application.user;

import com.ticketcorner.movieticketbooking.api.v1.ro.request.ConfigRequest;
import com.ticketcorner.movieticketbooking.configuration.SystemConfig;
import com.ticketcorner.movieticketbooking.dto.UserDTO;
import com.ticketcorner.movieticketbooking.common.enums.UserRoleEnum;
import com.ticketcorner.movieticketbooking.common.exceptions.UserNotFoundException;
import com.ticketcorner.movieticketbooking.common.mappers.UserMapper;
import com.ticketcorner.movieticketbooking.domain.user.entity.User;
import com.ticketcorner.movieticketbooking.domain.user.repository.UserRepository;
import com.ticketcorner.movieticketbooking.application.movie.MovieServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class handling admin-related functionalities, including system configuration and user management.
 */
@Service
public class AdminService extends UserService {

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

        logger.info("System configured: Total Tickets: {}, Max Ticket Capacity: {}, Ticket Release Rate: {}, Customer Retrieval Rate: {}", configRequest.getTotalTickets(), configRequest.getMaxTicketCapacity(), configRequest.getTicketReleaseRate(), configRequest.getCustomerRetrievalRate());
    }

    /**
     * Retrieves all admins from the database.
     *
     * @return List of {@link UserDTO} representing admins.
     */
    public List<UserDTO> getAllUsers() {
        List<User> admins = userRepository.findUsersByRole(UserRoleEnum.ROLE_ADMIN);
        return admins.stream().map(userMapper::fromEntityToDto).toList();
    }

    /**
     * Retrieves an admin by their user ID.
     *
     * @param userId The user ID of the admin.
     * @return {@link UserDTO} representing the retrieved admin.
     * @throws UserNotFoundException If admin not found with the provided user ID.
     */
    public UserDTO getUserByUserId(String userId) {
        User existAdmin = userRepository.findUserByRoleAndUserId(UserRoleEnum.ROLE_ADMIN, userId).orElseThrow(() -> new UserNotFoundException("Admin not found with the id: " + userId));

        return userMapper.fromEntityToDto(existAdmin);
    }

    /**
     * Updates an existing admin information.
     *
     * @param userId  The user ID of the admin to update.
     * @param userDTO The updated admin information.
     * @return {@link UserDTO} representing the updated admin.
     * @throws UserNotFoundException If admin not found with the provided user ID.
     */
    public UserDTO updateUser(String userId, UserDTO userDTO) {
        User existingAdmin = userRepository.findUserByRoleAndUserId(UserRoleEnum.ROLE_ADMIN, userId).orElseThrow(() -> new UserNotFoundException("Admin not found with the id: " + userId));

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
    public void deleteUser(String userId) {
        User existAdmin = userRepository.findUserByRoleAndUserId(UserRoleEnum.ROLE_ADMIN, userId).orElseThrow(() -> new UserNotFoundException("Admin not found with the id: " + userId));

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