package com.example.springdemo.service;

import com.example.springdemo.exception.InsufficientAmountException;
import com.example.springdemo.model.ShoppingCart;
import com.example.springdemo.model.User;
import com.example.springdemo.repository.DangerUserRepository;
import com.example.springdemo.repository.SecureUserRepository;
import com.example.springdemo.repository.UserRepository;
import com.example.springdemo.validation.SqlInputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    private ShoppingCartService shoppingCartService;

    public UserService(UserRepository userRepository, @Lazy ShoppingCartService shoppingCartService ){
        this.userRepository = userRepository;
        this.shoppingCartService = shoppingCartService;
    }

    @Autowired
    private DangerUserRepository dangerUserRepository;

    @Autowired
    private SecureUserRepository secureUserRepository;

    public List<User> getUserDanger(String username) {
        return dangerUserRepository.findUserByUsername(username);
    }

    public List<User> getUserSecure(String username) {
        return secureUserRepository.findUserByUsername(username);
    }

    public List<User> getUserSecureWithJPA(String username) {
        User user = userRepository.findByUsername(username);
        List<User> listOfUsers = new ArrayList<User>();
        if(user == null){
            User userMessage = new User();
            userMessage.setMessage("USER IS NULL!");
            listOfUsers.add(userMessage);
            return listOfUsers;
        }
        listOfUsers.add(user);
        return listOfUsers;
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
            user.setActive("true");
            user.setId(1);
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
        int userId = user.getId();
        return userId;

    }

    public int getUserIDwithUsername(String username) {
        User user = userRepository.findByUsername(username);
        int userId = user.getId();
        return userId;

    }

    public boolean validateWallet(int receiverID, int newAccountBalance) {
        ShoppingCart shoppingCartReceiver = shoppingCartService.getShoppingCart(receiverID);
        int wallet = shoppingCartReceiver.getWallet();
        if (wallet < newAccountBalance) {
            throw new InsufficientAmountException("Insufficient fund!");
        } else {
            return true;
        }
    }

    @Transactional
    public void transferMoney(String receiverName, int amount){
       int senderId = getUserID();
       ShoppingCart shoppingCartSender = shoppingCartService.getShoppingCart(senderId);
       int accountBalance = shoppingCartSender.getWallet();
       int newAccountBalance = accountBalance - amount;
       shoppingCartService.updateShoppingCartTransfer(newAccountBalance, senderId);

       int receiverId = getUserIDwithUsername(receiverName);
       ShoppingCart shoppingCartReceiver = shoppingCartService.getShoppingCart(receiverId);
       accountBalance = shoppingCartReceiver.getWallet();
       newAccountBalance = accountBalance + amount;
       shoppingCartService.updateShoppingCartTransfer(newAccountBalance, receiverId);
       validateWallet(receiverId,newAccountBalance);

    }
}
