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
import com.dilruk.movieticketbooking.services.thread.TicketProcessor;
import com.dilruk.movieticketbooking.services.thread.VendorThread;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class handling vendor related functionalities.
 */
@Service
public class VendorService extends UserService {

    public VendorService(UserRepository userRepository, MovieServiceImpl movieService, UserMapper userMapper) {
        super(userRepository, userMapper, movieService);
    }

    /**
     * Retrieves all vendors from the database.
     *
     * @return List of {@link UserDTO} representing vendors.
     */
    public List<UserDTO> getAllUsers() {
        List<User> vendors = userRepository.findUsersByRole(UserRole.ROLE_VENDOR);
        return vendors.stream().map(userMapper::fromEntityToDto).toList();
    }

    /**
     * Retrieves a vendor by their user ID.
     *
     * @param userId The user ID of the vendor.
     * @return {@link UserDTO} representing the retrieved vendor.
     * @throws UserNotFoundException If vendor not found with the provided user ID.
     */
    public UserDTO getUserByUserId(String userId) {
        User existVendor = userRepository.findUserByRoleAndUserId(UserRole.ROLE_CUSTOMER, userId)
                .orElseThrow(() -> new UserNotFoundException("Vendor not found with the id: " + userId));

        return userMapper.fromEntityToDto(existVendor);
    }

    /**
     * Updates an existing vendor's information.
     *
     * @param userId  The user ID of the vendor to update.
     * @param userDTO The updated vendor information.
     * @return {@link UserDTO} representing the updated vendor.
     * @throws UserNotFoundException If vendor not found with the provided user ID.
     */
    public UserDTO updateUser(String userId, UserDTO userDTO) {
        User existingVendor = userRepository.findUserByRoleAndUserId(UserRole.ROLE_VENDOR, userId)
                .orElseThrow(() -> new UserNotFoundException("Vendor not found with the id: " + userId));

        existingVendor.setName(userDTO.getName());
        existingVendor.setEmail(userDTO.getEmail());
        existingVendor.setPassword(userDTO.getPassword());
        existingVendor.setRole(userDTO.getRole());

        User updatedVendor = userRepository.save(existingVendor);
        logger.info("Vendor updated: {}", updatedVendor);

        return userMapper.fromEntityToDto(updatedVendor);
    }

    /**
     * Deletes a vendor from the database.
     *
     * @param userId The user ID of the vendor to delete.
     * @throws UserNotFoundException If vendor not found with the provided user ID.
     */
    public void deleteUser(String userId) {
        User existVendor = userRepository.findUserByRoleAndUserId(UserRole.ROLE_VENDOR, userId)
                .orElseThrow(() -> new UserNotFoundException("Vendor not found with the id: " + userId));

        userRepository.delete(existVendor);
        logger.info("Vendor deleted: {}", existVendor);
    }

    /**
     * Overrides the base method to perform a vendor specific action.
     * <p>
     * This implementation creates a new `VendorThread` instance with the provided configuration and ticket pool.
     *
     * @param systemConfig    The system configuration object containing relevant settings for the vendor.
     * @param ticketProcessor the ticket processor object used by the thread to process tickets
     */
    public void performAction(SystemConfig systemConfig, TicketProcessor ticketProcessor) {
        new VendorThread(systemConfig, ticketProcessor).start();
        logger.info("Vendor thread started");
    }

    /**
     * Creates a new movie.
     *
     * @param movieDTO The movie data to create.
     * @return The created movie as a MovieDTO.
     */
    public MovieDTO createMovie(MovieDTO movieDTO) {
        return movieService.createMovie(movieDTO);
    }
}
