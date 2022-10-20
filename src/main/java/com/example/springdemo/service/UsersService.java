package com.example.springdemo.service;

import com.example.springdemo.model.UsersModel;
import com.example.springdemo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    public UsersModel registerUser( String login, String password, String email){
        if(login == null && password == null){
            return null;
        }
        else{
            if(usersRepository.findFirstByLogin(login).isPresent()){
                System.out.println("Duplicate login");
                return null;
            }
            /*BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(password);*/
            UsersModel usersModel = new UsersModel();
            usersModel.setLogin(login);
            usersModel.setPassword(password);
            usersModel.setEmail(email);

            return usersRepository.save(usersModel);
        }
    }

    public UsersModel authenticate(String login, String password){
            return usersRepository.findByLoginAndPassword(login, password).orElse(null);
    }

}
