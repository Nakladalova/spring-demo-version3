package com.example.springdemo.repository;

import com.example.springdemo.model.User;
import java.util.List;

public interface CustomUserRepository {

    List<User> findUserByUsername(String username);



}
