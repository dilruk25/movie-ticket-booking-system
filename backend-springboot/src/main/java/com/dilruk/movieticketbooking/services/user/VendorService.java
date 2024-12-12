package com.dilruk.movieticketbooking.services.user;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.dtos.EventDTO;
import com.dilruk.movieticketbooking.dtos.MovieDTO;
import com.dilruk.movieticketbooking.dtos.UserDTO;
import com.dilruk.movieticketbooking.enums.UserRole;
import com.dilruk.movieticketbooking.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.mappers.UserMapper;
import com.dilruk.movieticketbooking.models.TicketPool;
import com.dilruk.movieticketbooking.models.user.User;
import com.dilruk.movieticketbooking.repositories.UserRepository;
import com.dilruk.movieticketbooking.services.event.EventServiceImpl;
import com.dilruk.movieticketbooking.services.movie.MovieServiceImpl;
import com.dilruk.movieticketbooking.services.thread.VendorThread;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class handling vendor related functionalities.
 */
@Service
public class VendorService extends AbstractUserService {

    public VendorService(UserRepository userRepository, MovieServiceImpl movieService, EventServiceImpl eventService, UserMapper userMapper) {
        super(userRepository, userMapper, movieService, eventService);
    }

    /**
     * Retrieves all vendors from the database.
     *
     * @return List of {@link UserDTO} representing vendors.
     */
    @Override
    public List<UserDTO> getAllUsers() {
        List<User> vendors = userRepository.findUsersByRole(UserRole.VENDOR);
        return vendors.stream().map(userMapper::fromEntityToDto).toList();
    }

    /**
     * Retrieves a vendor by their user ID.
     *
     * @param userId The user ID of the vendor.
     * @return {@link UserDTO} representing the retrieved vendor.
     * @throws UserNotFoundException If vendor not found with the provided user ID.
     */
    @Override
    public UserDTO getUserByUserId(String userId) {
        User existVendor = userRepository.findUserByRoleAndUserId(UserRole.CUSTOMER, userId)
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
    @Override
    public UserDTO updateUser(String userId, UserDTO userDTO) {
        User existingVendor = userRepository.findUserByRoleAndUserId(UserRole.VENDOR, userId)
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
    @Override
    public void deleteUser(String userId) {
        User existVendor = userRepository.findUserByRoleAndUserId(UserRole.VENDOR, userId)
                .orElseThrow(() -> new UserNotFoundException("Vendor not found with the id: " + userId));

        userRepository.delete(existVendor);
        logger.info("Vendor deleted: {}", existVendor);
    }

    /**
     * Overrides the base method to perform a vendor specific action.
     *
     * This implementation creates a new `VendorThread` instance with the provided configuration and ticket pool.
     *
     * @param systemConfig The system configuration object containing relevant settings for the vendor.
     * @param ticketPool The shared ticket pool used for adding tickets.
     */
    @Override
    public void performAction(SystemConfig systemConfig, TicketPool ticketPool) {
        new VendorThread(systemConfig, ticketPool).start();
        logger.info("Vendor thread started");
    }

    /**
     * Retrieves a list of all events.
     *
     * @return A list of EventDTOs representing all events.
     */
    public List<EventDTO> findAllEvents() {
        return eventService.findAllEvents();
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

    /**
     * Adds a new event.
     *
     * @param eventDTO The event data to create.
     * @return The created event as an EventDTO.
     */
    public EventDTO addEvent(EventDTO eventDTO) {
        return eventService.createEvent(eventDTO);
    }

    /**
     * Updates an existing event.
     *
     * @param id The ID of the event to update.
     * @param eventDTO The updated event data.
     * @return The updated event as an EventDTO.
     */
    public EventDTO updateEvent(String id, EventDTO eventDTO) {
        return eventService.updateEvent(id, eventDTO);
    }

    /**
     * Deletes an event.
     *
     * @param id The ID of the event to delete.
     */
    public void deleteEvent(String id) {
        eventService.deleteEvent(id);
    }

    /**
     * Finds events associated with a specific vendor.
     *
     * @param userId The user ID of the vendor.
     * @return A list of EventDTOs associated with the vendor.
     */
    public List<EventDTO> findEventsByVendorUserId(String userId) {
        return eventService.findEventsByVendorId(userId);
    }
}
