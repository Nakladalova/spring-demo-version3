package com.example.springdemo.service;

import com.example.springdemo.model.Item;
import com.example.springdemo.model.ShoppingCart;
import com.example.springdemo.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private UserService userService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, UserService userService){
        this.shoppingCartRepository = shoppingCartRepository;
        this.userService = userService;
    }

    public ShoppingCart getShoppingCart (int userID){
        if(userID == -1){
            userID = userService.getUserID();
        }
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUser_id(userID);
        if(shoppingCart==null){
            userID = userService.getUserID();
            shoppingCart = shoppingCartRepository.findShoppingCartByUser_id(userID);
        }
        return shoppingCart;
    }

    public ShoppingCart getTotal(List<Item> itemsList){
        int total=0;
        for (int i = 0; i < itemsList.size(); i++) {
            Item item = itemsList.get(i);
            int subtotal = item.getAmount() * item.getPrice();
            total = total + subtotal;
        }

        //shoppingCart.setTotal(total);
        int user_id = userService.getUserID();
        shoppingCartRepository.updateTotal(total,user_id);
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUser_id(user_id);
        return shoppingCart;
    }
}
