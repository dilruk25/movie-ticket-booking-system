package com.dilruk.movieticketbooking.mappers;

import com.dilruk.movieticketbooking.api.request.UserRequest;
import com.dilruk.movieticketbooking.dtos.UserDTO;
import com.dilruk.movieticketbooking.enums.IdPrefix;
import com.dilruk.movieticketbooking.models.user.User;
import com.dilruk.movieticketbooking.utils.IdGenerator;
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

        String role = userDTO.getRole().toUpperCase().replace(" ", "_");

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(role);
        switch (role) {
            case "ROLE_ADMIN" -> user.setUserId(IdGenerator.generateId(IdPrefix.ADMIN_PREFIX.getPrefix()));
            case "ROLE_CUSTOMER" -> user.setUserId(IdGenerator.generateId(IdPrefix.CUSTOMER_PREFIX.getPrefix()));
            case "ROLE_VENDOR" -> user.setUserId(IdGenerator.generateId(IdPrefix.VENDOR_PREFIX.getPrefix()));
            default -> throw new IllegalArgumentException("Invalid role");
        }

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
}