package com.dilruk.movieticketbooking.mappers;

import com.dilruk.movieticketbooking.api.request.UserRequest;
import com.dilruk.movieticketbooking.api.response.UserResponse;
import com.dilruk.movieticketbooking.dtos.UserDTO;
import com.dilruk.movieticketbooking.models.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO fromRequestToDto(UserRequest request) {
        UserDTO userDTO = new UserDTO();

        userDTO.setName(request.getName());
        userDTO.setEmail(request.getEmail());
        userDTO.setPassword(request.getPassword());
        userDTO.setRole(request.getRole());

        return userDTO;
    }

    public User fromDtoToEntity(UserDTO userDTO) {
        User user = new User();

        user.setUserId(userDTO.getUserId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());

        return user;
    }

    public UserDTO fromEntityToDto(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setUserId(user.getUserId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());

        return userDTO;
    }

    public UserResponse fromDtoToResponse(UserDTO userDTO) {
        UserResponse response = new UserResponse();

        response.setUserId(userDTO.getUserId());
        response.setName(userDTO.getName());
        response.setEmail(userDTO.getEmail());
        response.setRole(userDTO.getRole());

        return response;
    }
}