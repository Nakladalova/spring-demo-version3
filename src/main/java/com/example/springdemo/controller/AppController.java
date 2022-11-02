package com.example.springdemo.controller;

import com.example.springdemo.User;
import com.example.springdemo.model.Employee;
import com.example.springdemo.repository.DangerEmployeeRepository;
import com.example.springdemo.repository.UserRepository;
import com.example.springdemo.service.EmployeeService;
import com.example.springdemo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Controller
@EnableWebMvc
public class AppController extends WebMvcConfigurerAdapter {

	private final UsersService usersService;

	@Autowired
	private EmployeeService service;

	//private DangerEmployeeRepository dangerEmployeeRepository;

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
		registry.addViewController("/admin").setViewName("admin");
		registry.addViewController("/search").setViewName("employee_page");
	}

	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}

	@GetMapping("/Home")
	public String viewePage() {
		return "Home";
	}

	@GetMapping("/admin")
	public String viewAdminPage() {
		return "admin";
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

	@GetMapping("/search")
	public String add(Model model) {
		//List<Employee> listemployee = service.listAll();
		model.addAttribute("employee", new Employee());
		return "employee_page";
	}


	@PostMapping("/search")
	public String doSearchEmployee(@ModelAttribute("employeeSearchFormData") Employee formData, Model model) {
		//Employee emp = service.get(formData.getId());
		Employee emp = service.getName(formData.getUsername());
		model.addAttribute("employee", emp);
		return "employee_page";
	}



	/*@GetMapping("/all")
    public String add(Model model) {
        List<Employee> listemployee = service.listAll();
        model.addAttribute("employee", new Employee());
        return "employee_page";
    }*/

}
