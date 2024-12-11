package com.dilruk.movieticketbooking.services;

import com.dilruk.movieticketbooking.dtos.EventDTO;
import com.dilruk.movieticketbooking.dtos.UserDTO;
import com.dilruk.movieticketbooking.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.mappers.UserMapper;
import com.dilruk.movieticketbooking.models.User;
import com.dilruk.movieticketbooking.repositories.UserRepository;
import com.dilruk.movieticketbooking.services.event.EventServiceImpl;
import com.dilruk.movieticketbooking.services.user.AbstractUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService extends AbstractUserService {

    public AdminService(UserRepository userRepository, MovieServiceImpl movieService, EventServiceImpl eventService, UserMapper userMapper) {
        super(userRepository, movieService, eventService, userMapper);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::fromEntityToDto).toList();
    }

    @Override
    public UserDTO findUserByUserId(String userId) {
        User existUser = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with the id: " + userId));

        return userMapper.fromEntityToDto(existUser);
    }

    @Override
    public UserDTO updateUser(String userId, UserDTO userDTO) {
        User existingUser = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with the id: " + userId));

        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPassword(userDTO.getPassword());
        existingUser.setRole(userDTO.getRole());

        User updatedUser = userRepository.save(existingUser);

        return userMapper.fromEntityToDto(updatedUser);
    }

    @Override
    public void deleteUser(String userId) {
        User existUser = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with the id: " + userId));

        userRepository.delete(existUser);
    }

    @Override
    public List<EventDTO> findAllEvents() {
        return eventService.findAllEvents();
    }
}
