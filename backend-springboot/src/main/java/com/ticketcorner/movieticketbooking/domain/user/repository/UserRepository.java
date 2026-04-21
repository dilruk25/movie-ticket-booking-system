package com.ticketcorner.movieticketbooking.domain.user.repository;

import com.ticketcorner.movieticketbooking.common.enums.UserRoleEnum;
import com.ticketcorner.movieticketbooking.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findUsersByRole(UserRoleEnum role);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByRoleAndEmail(UserRoleEnum userRoleEnum, String email);

    Optional<User> findUserByRoleAndUserId(UserRoleEnum userRoleEnum, String userId);

    Optional<User> findUserByRoleAndName(UserRoleEnum userRoleEnum, String userName);
}
