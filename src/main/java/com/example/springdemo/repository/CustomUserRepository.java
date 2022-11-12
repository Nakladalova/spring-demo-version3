package com.example.springdemo.repository;

import com.example.springdemo.model.User;

import java.util.Optional;

public interface CustomUserRepository {

    Optional<User> findUserByUsername(String username);

    //Optional<Employee> findEmployeeById(int Id);

    //Optional<Employee> findEmployeeByUsername(String username);
}
