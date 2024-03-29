package com.example.springdemo.service;

import com.example.springdemo.model.Item;
import com.example.springdemo.model.ShoppingCart;
import com.example.springdemo.repository.ItemRepository;
import com.example.springdemo.repository.ShoppingCartRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.lang.Math;

import java.util.List;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private UserService userService;
    private final ItemRepository itemRepository;

    public ShoppingCartService( ShoppingCartRepository shoppingCartRepository, @Lazy UserService userService, ItemRepository itemRepository){
        this.shoppingCartRepository = shoppingCartRepository;
        this.userService = userService;
        this.itemRepository = itemRepository;
    }

    public ShoppingCart getShoppingCart (int userID){
        if(userID == -1){
            userID = userService.getUserID();
        }
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserId(userID);
        if(shoppingCart==null){
            userID = userService.getUserID();
            shoppingCart = shoppingCartRepository.findShoppingCartByUserId(userID);
        }
        return shoppingCart;
    }

    public ShoppingCart getTotal(List<Item> itemsList){
        int total=0;
        for (int i = 0; i < itemsList.size(); i++) {
            Item item = itemsList.get(i);
            int subtotal = item.getAmount() * item.getPrice();
            /*if (subtotal < 0) {
                throw new RuntimeException("Integer Overflow");
            }*/
            total = total + subtotal;
        }
        int userID = userService.getUserID();
        shoppingCartRepository.updateTotal(total,userID);
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserId(userID);
        return shoppingCart;
    }

    public int calculateAccountBallance() {
        ShoppingCart shoppingCart = getShoppingCart(-1);
        int total = shoppingCart.getTotal();
        int wallet = shoppingCart.getWallet();
        int result = wallet - total;
        return result;
    }

    public ShoppingCart updateShoppingCart(){
        int result = calculateAccountBallance();
        ShoppingCart shoppingCart = new ShoppingCart();
        if(result < 0){
            shoppingCart.setWallet(result);
            shoppingCart.setTotal(0);
            return shoppingCart;
        }
        int userID = userService.getUserID();
        shoppingCartRepository.updateShoppingCart(0,result,userID);
        long userIDlong = (long)userID;
        itemRepository.deleteItems(userIDlong);
        shoppingCart.setWallet(result);
        shoppingCart.setTotal(0);
        return shoppingCart;
    }

    public void updateShoppingCartTransfer(int amount, int senderID){
        shoppingCartRepository.updateWallet(amount, senderID);
    }
}
