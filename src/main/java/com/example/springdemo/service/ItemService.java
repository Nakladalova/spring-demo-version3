package com.example.springdemo.service;

import com.example.springdemo.model.Item;
import com.example.springdemo.model.User;
import com.example.springdemo.repository.ItemRepository;
import com.example.springdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private ShoppingCartService shoppingCartService;

    public ItemService(ItemRepository itemRepository, ShoppingCartService shoppingCartService){
        this.itemRepository = itemRepository;
        this.shoppingCartService = shoppingCartService;
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    //@Transactional
    public void addItem(int productId, int amount, int price) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userRepository.findByUsername(currentPrincipalName);
        int userId = user.getId();
        Integer amountInDB = findItem(productId, userId);
        if(amountInDB > 0){
            int updatedAmount = amountInDB + amount;
            itemRepository.updateItem(updatedAmount, productId, userId);
            return;
        }
        Item item = new Item();
        item.setProductId(productId);
        item.setAmount(amount);
        item.setShoppingcartId(userId);
        item.setPrice(price);
        itemRepository.save(item);
        //shoppingCartService.getTotal(getItemsFromDB());
        return;
    }

    public List getItemsFromDB (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userRepository.findByUsername(currentPrincipalName);
        int userId = user.getId();
        Collection<Item> itemsCollection = itemRepository.findAllItems(userId);
        List<Item> itemsList = itemsCollection.stream().collect(toList());
        return itemsList;
    }

    public int findItem(int productId, int shoppingcartId){
        Integer amount = itemRepository.findItem(productId,shoppingcartId );
        if (amount == null){
            return 0;
        }
        return amount;
    }

    public void deleteItem(String productName) {
        int shoppingCartId = userService.getUserID();
        int productId = productService.getProductByName(productName);
        itemRepository.deleteItem(shoppingCartId, productId);
    }
}
