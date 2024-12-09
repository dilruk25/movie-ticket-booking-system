package com.dilruk.movieticketbooking.user.services;

import com.dilruk.movieticketbooking.common.enums.UserRole;
import com.dilruk.movieticketbooking.common.exceptions.BadUserCredentialException;
import com.dilruk.movieticketbooking.common.exceptions.UserNotFoundException;
import com.dilruk.movieticketbooking.common.mappers.CustomerMapper;
import com.dilruk.movieticketbooking.common.mappers.VendorMapper;
import com.dilruk.movieticketbooking.user.models.Customer;
import com.dilruk.movieticketbooking.user.models.User;
import com.dilruk.movieticketbooking.user.models.Vendor;
import com.dilruk.movieticketbooking.user.repositories.CustomerRepository;
import com.dilruk.movieticketbooking.user.repositories.UserRepository;
import com.dilruk.movieticketbooking.user.repositories.VendorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public AtomicBoolean login(String email, String password) {

        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new BadUserCredentialException(email + "is not found"));

        if (!user.getPassword().equals(password)) {
            throw new BadUserCredentialException("password is not correct");
        }

        if (user instanceof Customer) {
            log.info("Customer login");
        } else if (user instanceof Vendor) {
            log.info("Vendor login");
        }
        return new AtomicBoolean(true);
    }
}
