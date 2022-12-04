package com.example.springdemo.controller;

import com.example.springdemo.model.User;
import com.example.springdemo.repository.UserRepository;
import com.example.springdemo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

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

	@GetMapping("/access_denied")
	public String viewePaget() {
		return "access_denied";
	}
	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		
		return "signup_form";
	}
	
	@PostMapping("/register")
	public String processRegister(@Valid User user, BindingResult bindingResult) {
		if(bindingResult.hasErrors()){
			return "signup_form";
		}
		User registeredUser = usersService.registerUser(user.getUsername(), user.getPassword(), user.getEmail());
		if(registeredUser==null){
			return "error";
		}
		return "redirect:/";
	}
	
	@GetMapping("/users")
	public String listUsers(Model model) {
		List<User> listUsers = userRepo.findAll();
		model.addAttribute("listUsers", listUsers);
		return "users";
	}

	@GetMapping("/user_page")
	public String getInfo(Model model) {
		//List<Employee> listemployee = service.listAll();
		model.addAttribute("user", new User());
		return "user_page";
	}


	@PostMapping("/user_page")
	public String getInfoOfUser(@ModelAttribute("userSearchFormData") User formData, Model model) {
		User userDanger = usersService.getUserDanger(formData.getUsername());
		User userSecure = usersService.getUserSecure(formData.getUsername());
		User userSecureWithJPA = usersService.getUserSecureWithJPA(formData.getUsername());

		model.addAttribute("user", userDanger);
		return "user_page";
	}

	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}

}
