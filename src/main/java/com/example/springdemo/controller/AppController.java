package com.example.springdemo.controller;

import com.example.springdemo.model.Item;
import com.example.springdemo.model.Product;
import com.example.springdemo.model.ShoppingCart;
import com.example.springdemo.model.User;
import com.example.springdemo.repository.UserRepository;
import com.example.springdemo.service.ItemService;
import com.example.springdemo.service.ProductService;
import com.example.springdemo.service.ShoppingCartService;
import com.example.springdemo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@EnableTransactionManagement
@Controller
@EnableWebMvc
public class AppController extends WebMvcConfigurerAdapter {

	private static final Logger logger = LogManager.getLogger(AppController.class);

	private final UserService userService;
	private final ItemService itemService;
	private ShoppingCartService shoppingCartService;

	public AppController(UserService usersService, ItemService itemService, ShoppingCartService shoppingCartService) {
		this.userService = usersService;
		this.itemService = itemService;
		this.shoppingCartService = shoppingCartService;
	}

	@Autowired
	private ProductService productService;

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
	
	@GetMapping("/products")
	public String viewHomeP() {
		return "products";
	}

	@GetMapping("/watchdetail")
	public String getById(Model model, @RequestParam (defaultValue = "1") int id){
		Product product = productService.getProduct(id);
		model.addAttribute("product", product);
		return "watch_detail";
	}

	@PostMapping("/watchdetail")
	public String addItem(@RequestParam("product_name") String product_name, @RequestParam("price") int price,Item item) {
		Integer id = productService.getProductByName(product_name);
		itemService.addItem(id,item.getAmount(),price);
		return "redirect:/shopping";
	}

	@GetMapping("/transfermoney")
	public String transferMoney(Model model){
		return "transfer_money";
	}

	@PostMapping("/transfermoney")
	public String transferMoney(@RequestParam("receiver") String receiver, @RequestParam("amount") int amount) {
		userService.transferMoney(receiver, amount);
		return "redirect:/shopping";
	}

	/*@GetMapping("")
	public String viewHomePage() {
		return "index";
	}*/

	@GetMapping("/")
	public String viewHomePag(Model model) {
		Product product = productService.getProduct(8);
		product.setPhotos_image_path(product.getPhotos_image_path());
		model.addAttribute("product", product);
		return "home_page";
	}


	@GetMapping("/addProduct")
	public String showAddProduct()
	{
		return "addProduct";
	}

	@PostMapping("/addProduct")
	public String saveProduct(@RequestParam("file") MultipartFile file,
							  @RequestParam("pname") String name,
							  @RequestParam("price") int price,
							  @RequestParam("desc") String desc) throws IOException {
		productService.saveProductToDB(file, name, desc, price);
		return "redirect:/homepage";
	}

	@GetMapping("/image")
	public String viewImage(Model model) {
		Product product = productService.getProduct(8);
		model.addAttribute("product", product);
		return "image";
	}

	@GetMapping("/shopping")
	public String viewShopping(Model model) {
		List<Item> listItems = itemService.getItemsFromDB();
		ShoppingCart shoppingCart = shoppingCartService.getTotal(listItems);
		model.addAttribute("listItems", listItems);
		model.addAttribute("shoppingCart", shoppingCart);
		return "shopping";
	}

	@PostMapping("/shopping")
	public String deleteItem(@RequestParam("product_name") String product_name) {
        itemService.deleteItem(product_name);
		return "redirect:/shopping";

	}

	@GetMapping("/purchase")
	public String viewPurchase(Model model) {
		ShoppingCart shoppingCart = shoppingCartService.updateShoppingCart();
		model.addAttribute("shoppingCart", shoppingCart);
		return "purchase";
	}

	@GetMapping("/check")
	public String viewCheck(Model model, @RequestParam (defaultValue = "-1") int userID) { //, @RequestParam (defaultValue = "-1") int userID
		//int userID = userService.getUserID();
		ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(userID);
		model.addAttribute("shoppingCart", shoppingCart);
		return "check";
	}


	@GetMapping("/items")
	public String viewItems() {
		return "items";
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

		logger.info("Adding new user " + user.getUsername());
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


	@GetMapping("/user_pa")
	public String getInfoOfUser(@ModelAttribute("userSearchFormData") User formData, Model model) {
		User userDanger = userService.getUserDanger(formData.getUsername());
		User userSecure = userService.getUserSecure(formData.getUsername());
		User userSecureWithJPA = userService.getUserSecureWithJPA(formData.getUsername());

		model.addAttribute("user", userDanger);
		return "user_pa";
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
