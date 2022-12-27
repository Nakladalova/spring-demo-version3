package com.example.springdemo.service;

import com.example.springdemo.model.User;
import com.example.springdemo.repository.DangerUserRepository;
import com.example.springdemo.repository.SecureUserRepository;
import com.example.springdemo.repository.UserRepository;
import com.example.springdemo.validation.SqlInputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Autowired
    private DangerUserRepository dangerUserRepository;

    @Autowired
    private SecureUserRepository secureUserRepository;


    public User getUserDanger(String username) {
        return dangerUserRepository.findUserByUsername(username).get();
    }

    public User getUserSecure(String username) {
        return secureUserRepository.findUserByUsername(username).get();
    }

    public User getUserSecureWithJPA(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null){
            User userMessage = new User();
            userMessage.setMessage("USER IS NULL!");
            return userMessage;
        }
        return user;
    }

    /*@Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }*/

    public User registerUser(String username, String password, String email){
        if(username == null && password == null){
            return null;
        }
        else{
            if(userRepository.findByUsername(username)!=null){
                System.out.println("Duplicate login");
                return null;
            }
            //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
           //String encodedPassword = passwordEncoder.encode(password);
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setAccountNonLocked(true);
            user.setEnabled(true);
            return userRepository.save(user);
        }
    }

    public User deleteUser(String username) {
        long deletedRow = userRepository.deleteByUsername(username);
        User userMessage = new User();
        if(deletedRow==1){
            userMessage.setMessage("User "+ username +" is deleted.");
            return userMessage;
        }
        userMessage.setMessage("Failed to delete User");
        return userMessage;
    }

    public int getUserID() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userRepository.findByUsername(currentPrincipalName);
        int user_id = user.getId();
        return user_id;

    }

    public int getUserIDwithUsername(String username) {
        User user = userRepository.findByUsername(username);
        int user_id = user.getId();
        return user_id;

    }
}
