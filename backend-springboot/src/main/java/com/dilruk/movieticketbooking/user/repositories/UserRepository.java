package com.dilruk.movieticketbooking.user.repositories;

import com.dilruk.movieticketbooking.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUserId(String userId);
    Optional<User> findUserByName(String userName);
}
