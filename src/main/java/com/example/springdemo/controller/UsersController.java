package com.example.springdemo.controller;

import com.example.springdemo.model.UsersModel;
import com.example.springdemo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @ModelAttribute("user")
    public UsersModel usersModel() {
        return new UsersModel();
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("registerRequest", new UsersModel());
        return "register_page";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model){
        model.addAttribute("loginRequest", new UsersModel());
        return "login_page";
    }

    @PostMapping("/register")
    public String register( UsersModel usersModel){
        System.out.println("Register request " + usersModel);
        UsersModel registeredUser = usersService.registerUser(usersModel.getLogin(), usersModel.getPassword(), usersModel.getEmail());
        return registeredUser == null ? "error_page" : "redirect:/login";
    }

    @PostMapping("/login")
    public String login(UsersModel usersModel, Model model){
        System.out.println("Login request " + usersModel);
        UsersModel authenticated= usersService.authenticate(usersModel.getLogin(), usersModel.getPassword());
        if(authenticated!=null){
            model.addAttribute("userLogin", authenticated.getLogin());
            return "personal_page";
        }
        else{
            return "error_page";
        }
    }
}
