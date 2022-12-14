package com.example.springdemo.controller;

import com.example.springdemo.model.Item;
import com.example.springdemo.model.ShoppingCart;
import com.example.springdemo.model.User;
import com.example.springdemo.repository.UserRepository;
import com.example.springdemo.service.ItemService;
import com.example.springdemo.service.ShoppingCartService;
import com.example.springdemo.service.UserService;
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

	private final UserService userService;
	private final ItemService itemService;
	private ShoppingCartService shoppingCartService;

	public AppController(UserService usersService, ItemService itemService, ShoppingCartService shoppingCartService) {
		this.userService = usersService;
		this.itemService = itemService;
		this.shoppingCartService = shoppingCartService;
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/index").setViewName("index");
		registry.addViewController("/register").setViewName("signup_form");
		registry.addViewController("/register_success").setViewName("register_success");
		registry.addViewController("/users").setViewName("users");
		registry.addViewController("/delete_user").setViewName("delete_user");
	}

	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}

	@GetMapping("/shoppingcart")
	public String viewShoppingCart() {
		return "shoppingcart";
	}

	@GetMapping("/shopping")
	public String viewShopping(Model model) {
		List<Item> listItems = itemService.getItemsFromDB();
		ShoppingCart shoppingCart = shoppingCartService.getTotal(listItems);
		model.addAttribute("listItems", listItems);
		model.addAttribute("shoppingCart", shoppingCart);
		return "shopping";
	}

	@GetMapping("/check")
	public String viewCheck(Model model, @RequestParam (defaultValue = "-1") int userID) { //, @RequestParam (defaultValue = "-1") int userID
		//int userID = userService.getUserID();
		ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(userID);
		model.addAttribute("shoppingCart", shoppingCart);
		return "check";
	}

	@PostMapping("/check")
	public String getCheck() {
		return "check";
	}

	@GetMapping("/items")
	public String viewItems() {
		return "items";
	}

	@GetMapping("/watch1")
	public String viewWatch1() {
		return "watch1";
	}

	@PostMapping("/watch1")
	public String addItem(Item item) {
		Item item1 = itemService.addItem(1,item.getAmount());
		return "shopping";
	}

	@GetMapping("/watch2")
	public String viewWatch2() {
		return "watch2";
	}

	@PostMapping("/watch2")
	public String addItem2(Item item) {
		itemService.addItem(2,item.getAmount());
		return "shoppingcart";
	}

	@GetMapping("/watch3")
	public String viewWatch3() {
		return "watch3";
	}

	@PostMapping("/watch3")
	public String addItem3(Item item) {
		itemService.addItem(3,item.getAmount());
		return "shopping_cart";
	}

	@GetMapping("/watch4")
	public String viewWatch4() {
		return "watch4";
	}

	@GetMapping("/watch5")
	public String viewWatch5() {
		return "watch5";
	}

	@GetMapping("/watch6")
	public String viewWatch6() {
		return "watch6";
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
		User registeredUser = userService.registerUser(user.getUsername(), user.getPassword(), user.getEmail());
		if(registeredUser==null){
			return "signup_form";
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
	public String getInfo( Model model) {
		model.addAttribute("user", new User());
		return "user_page";
	}


	@PostMapping("/user_page")
	public String getInfoOfUser(@ModelAttribute("userSearchFormData") User formData, Model model) {
		User userDanger = userService.getUserDanger(formData.getUsername());
		User userSecure = userService.getUserSecure(formData.getUsername());
		User userSecureWithJPA = userService.getUserSecureWithJPA(formData.getUsername());

		model.addAttribute("user", userDanger);
		return "user_page";
	}

	@GetMapping("/delete_user")
	public String deleteUser( Model model) {
		model.addAttribute("user", new User());
		return "delete_user";
	}


	@PostMapping("/delete_user")
	public String deleteUserByUsername(User user, Model model) {
		User message = userService.deleteUser(user.getUsername());
		model.addAttribute("user", message );
		return "delete_user";

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
