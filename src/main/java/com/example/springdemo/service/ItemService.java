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

    public Item addItem(int product_id, int amount) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userRepository.findByUsername(currentPrincipalName);
        int user_id = user.getId();
        Item item = new Item();
        item.setProduct_id(product_id);
        item.setAmount(amount);
        item.setShoppingcart_id(user_id);
        return itemRepository.save(item);
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


}
