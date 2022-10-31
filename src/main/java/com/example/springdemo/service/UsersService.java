package com.example.springdemo.service;

import com.example.springdemo.User;
import com.example.springdemo.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UserRepository userRepository;

    public UsersService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    public User registerUser(String username, String password, String email){
        if(username == null && password == null){
            return null;
        }
        else{
            if(userRepository.findByUsername(username)!=null){
                System.out.println("Duplicate login");
                return null;
            }
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(password);
            User user = new User();
            user.setUsername(username);
            user.setPassword(encodedPassword);
            user.setEmail(email);
            user.setAccountNonLocked(true);
            user.setEnabled(true);
            return userRepository.save(user);
        }
    }

}
