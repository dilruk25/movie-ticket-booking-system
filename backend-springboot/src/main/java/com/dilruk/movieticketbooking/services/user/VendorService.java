package com.dilruk.movieticketbooking.services.user;

import com.dilruk.movieticketbooking.dtos.EventDTO;
import com.dilruk.movieticketbooking.dtos.MovieDTO;
import com.dilruk.movieticketbooking.dtos.UserDTO;
import com.dilruk.movieticketbooking.enums.UserRole;
import com.dilruk.movieticketbooking.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.mappers.UserMapper;
import com.dilruk.movieticketbooking.models.user.User;
import com.dilruk.movieticketbooking.repositories.UserRepository;
import com.dilruk.movieticketbooking.services.event.EventServiceImpl;
import com.dilruk.movieticketbooking.services.movie.MovieServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService extends AbstractUserService {

    public VendorService(UserRepository userRepository, MovieServiceImpl movieService, EventServiceImpl eventService, UserMapper userMapper) {
        super(userRepository, userMapper, movieService, eventService);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> vendors = userRepository.findUsersByRole(UserRole.VENDOR);
        return vendors.stream().map(userMapper::fromEntityToDto).toList();
    }

    @Override
    public UserDTO getUserByUserId(String userId) {
        User existVendor = userRepository.findUserByRoleAndUserId(UserRole.CUSTOMER, userId)
                .orElseThrow(() -> new UserNotFoundException("Vendor not found with the id: " + userId));

        return userMapper.fromEntityToDto(existVendor);
    }

    @Override
    public UserDTO updateUser(String userId, UserDTO userDTO) {
        User existingVendor = userRepository.findUserByRoleAndUserId(UserRole.VENDOR, userId)
                .orElseThrow(() -> new UserNotFoundException("Vendor not found with the id: " + userId));

        existingVendor.setName(userDTO.getName());
        existingVendor.setEmail(userDTO.getEmail());
        existingVendor.setPassword(userDTO.getPassword());
        existingVendor.setRole(userDTO.getRole());

        User updatedVendor = userRepository.save(existingVendor);

        return userMapper.fromEntityToDto(updatedVendor);
    }

    @Override
    public void deleteUser(String userId) {
        User existVendor = userRepository.findUserByRoleAndUserId(UserRole.VENDOR, userId)
                .orElseThrow(() -> new UserNotFoundException("Vendor not found with the id: " + userId));

        userRepository.delete(existVendor);
    }

    public List<EventDTO> findAllEvents() {
        return eventService.findAllEvents();
    }

    public MovieDTO createMovie(MovieDTO movieDTO) {
        return movieService.createMovie(movieDTO);
    }

    public EventDTO addEvent(EventDTO eventDTO) {
        return eventService.createEvent(eventDTO);
    }

    public EventDTO updateEvent(String id, EventDTO eventDTO) {
        return eventService.updateEvent(id, eventDTO);
    }

    public void deleteEvent(String id) {
        eventService.deleteEvent(id);
    }

    public List<EventDTO> findEventsByVendorUserId(String userId) {
        return eventService.findEventsByVendorId(userId);
    }
}
