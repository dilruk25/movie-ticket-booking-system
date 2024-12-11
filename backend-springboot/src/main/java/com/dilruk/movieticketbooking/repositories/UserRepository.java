package com.dilruk.movieticketbooking.repositories;

import com.dilruk.movieticketbooking.enums.UserRole;
import com.dilruk.movieticketbooking.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUserId(String userId);
    Optional<User> findUserByName(String userName);
    Optional<User> findUserByRole(UserRole role);
}
