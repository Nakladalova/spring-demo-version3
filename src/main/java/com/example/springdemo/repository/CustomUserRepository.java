package com.example.springdemo.repository;

import com.example.springdemo.model.User;

import java.util.List;
import java.util.Optional;

public interface CustomUserRepository {

    //Optional<User> findUserByUsername(String username);

    List<User> findUserByUsername(String username);



}
