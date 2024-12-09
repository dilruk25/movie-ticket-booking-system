package com.dilruk.movieticketbooking.user.services;

import com.dilruk.movieticketbooking.user.models.User;

import java.util.concurrent.atomic.AtomicBoolean;

public interface UserService {

    AtomicBoolean login(String email, String password);

}
