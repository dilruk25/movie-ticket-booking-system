package com.dilruk.movieticketbooking.repositories;

import com.dilruk.movieticketbooking.enums.UserRole;
import com.dilruk.movieticketbooking.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findUsersByRole(UserRole role);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByRoleAndEmail(UserRole userRole, String email);

    Optional<User> findUserByRoleAndUserId(UserRole userRole, String userId);

    Optional<User> findUserByRoleAndName(UserRole userRole, String userName);
}
