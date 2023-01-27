package com.example.springdemo.service;

import com.example.springdemo.model.Item;
import com.example.springdemo.model.ShoppingCart;
import com.example.springdemo.model.User;
import com.example.springdemo.repository.ItemRepository;
import com.example.springdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    public void addItem(int product_id, int amount, int price) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userRepository.findByUsername(currentPrincipalName);
        int user_id = user.getId();
        Integer amount2 = findItem(product_id, user_id);
        if(amount2 > 0){
            int updatedAmount = amount2 + amount;
            itemRepository.updateItem(updatedAmount, product_id, user_id);
            return;
        }
        Item item2 = new Item();
        item2.setProduct_id(product_id);
        item2.setAmount(amount);
        item2.setShoppingcart_id(user_id);
        item2.setPrice(price);
        itemRepository.save(item2);
        return;
    }

    public List getItemsFromDB (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userRepository.findByUsername(currentPrincipalName);
        int user_id = user.getId();
        Collection<Item> itemsCollection = itemRepository.findAllItems(user_id);
        List<Item> itemsList = itemsCollection.stream().collect(toList());
        return itemsList;
    }

    public int findItem(int product_id, int shoppingcart_id){
        //Item item = itemRepository.findItem(product_id,shoppingcart_id );
        Integer amount = itemRepository.findItem(product_id,shoppingcart_id );
        if (amount == null){
            return 0;
        }
        return amount;
    }

    public void deleteItem(String product_name) {
        int shoppingCartID = userService.getUserID();
        int productID = productService.getProductByName(product_name);
        itemRepository.deleteItem(shoppingCartID, productID);
    }
}
