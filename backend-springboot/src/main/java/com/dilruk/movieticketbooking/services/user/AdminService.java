package com.dilruk.movieticketbooking.services.user;

import com.dilruk.movieticketbooking.dtos.EventDTO;
import com.dilruk.movieticketbooking.dtos.UserDTO;
import com.dilruk.movieticketbooking.enums.UserRole;
import com.dilruk.movieticketbooking.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.mappers.UserMapper;
import com.dilruk.movieticketbooking.models.User;
import com.dilruk.movieticketbooking.repositories.UserRepository;
import com.dilruk.movieticketbooking.services.event.EventServiceImpl;
import com.dilruk.movieticketbooking.services.movie.MovieServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService extends AbstractUserService {

    public AdminService(UserRepository userRepository, MovieServiceImpl movieService, EventServiceImpl eventService, UserMapper userMapper) {
        super(userRepository, userMapper, movieService, eventService);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> admins = userRepository.findUsersByRole(UserRole.ADMIN);
        return admins.stream().map(userMapper::fromEntityToDto).toList();
    }

    @Override
    public UserDTO getUserByUserId(String userId) {
        User existAdmin = userRepository.findUserByRoleAndUserId(UserRole.ADMIN, userId)
                .orElseThrow(() -> new UserNotFoundException("Admin not found with the id: " + userId));

        return userMapper.fromEntityToDto(existAdmin);
    }

    @Override
    public UserDTO updateUser(String userId, UserDTO userDTO) {
        User existingAdmin = userRepository.findUserByRoleAndUserId(UserRole.ADMIN, userId)
                .orElseThrow(() -> new UserNotFoundException("Admin not found with the id: " + userId));

        existingAdmin.setName(userDTO.getName());
        existingAdmin.setEmail(userDTO.getEmail());
        existingAdmin.setPassword(userDTO.getPassword());
        existingAdmin.setRole(userDTO.getRole());

        User updatedAdmin = userRepository.save(existingAdmin);

        return userMapper.fromEntityToDto(updatedAdmin);
    }

    @Override
    public void deleteUser(String userId) {
        User existAdmin = userRepository.findUserByRoleAndUserId(UserRole.ADMIN, userId)
                .orElseThrow(() -> new UserNotFoundException("Admin not found with the id: " + userId));

        userRepository.delete(existAdmin);
    }

    public List<EventDTO> getAllEvents() {
        return eventService.findAllEvents();
    }
}
