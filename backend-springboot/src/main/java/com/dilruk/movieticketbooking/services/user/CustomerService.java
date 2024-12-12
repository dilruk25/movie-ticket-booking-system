package com.dilruk.movieticketbooking.services.user;

import com.dilruk.movieticketbooking.dtos.MovieDTO;
import com.dilruk.movieticketbooking.dtos.UserDTO;
import com.dilruk.movieticketbooking.enums.UserRole;
import com.dilruk.movieticketbooking.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.mappers.UserMapper;
import com.dilruk.movieticketbooking.models.user.User;
import com.dilruk.movieticketbooking.repositories.UserRepository;
import com.dilruk.movieticketbooking.services.movie.MovieServiceImpl;
import com.dilruk.movieticketbooking.services.event.EventServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService extends AbstractUserService {

    public CustomerService(UserRepository userRepository, MovieServiceImpl movieService, EventServiceImpl eventService, UserMapper userMapper) {
        super(userRepository, userMapper, movieService, eventService);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> customers = userRepository.findUsersByRole(UserRole.CUSTOMER);
        return customers.stream().map(userMapper::fromEntityToDto).toList();
    }

    @Override
    public UserDTO getUserByUserId(String userId) {
        User existCustomer = userRepository.findUserByRoleAndUserId(UserRole.CUSTOMER, userId)
                .orElseThrow(() -> new UserNotFoundException("Customer not found with the id: " + userId));

        return userMapper.fromEntityToDto(existCustomer);
    }

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

    @Override
    public void deleteUser(String userId) {
        User existCustomer = userRepository.findUserByRoleAndUserId(UserRole.CUSTOMER, userId)
                .orElseThrow(() -> new UserNotFoundException("Customer not found with the id: " + userId));

        userRepository.delete(existCustomer);
    }

    public List<MovieDTO> getAllMovies() {
        return movieService.getAllMovies();
    }
}
