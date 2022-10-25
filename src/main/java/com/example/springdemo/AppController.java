package com.example.springdemo;

import java.util.List;

import com.example.springdemo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Controller
@EnableWebMvc
public class AppController extends WebMvcConfigurerAdapter {

	private final UsersService usersService;

	public AppController(UsersService usersService) {
		this.usersService = usersService;
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/index").setViewName("index");
		registry.addViewController("/register").setViewName("signup_form");
		registry.addViewController("/register_success").setViewName("register_success");
		registry.addViewController("/users").setViewName("users");
	}

	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}
	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		
		return "signup_form";
	}
	
	@PostMapping("/register")
	public String processRegister(User user) {
		User registeredUser = usersService.registerUser(user.getUsername(), user.getPassword(), user.getEmail());
		return registeredUser == null ? "error_page" : "redirect:/register_success";

	}
	
	@GetMapping("/users")
	public String listUsers(Model model) {
		List<User> listUsers = userRepo.findAll();
		model.addAttribute("listUsers", listUsers);
		
		return "users";
	}

}
