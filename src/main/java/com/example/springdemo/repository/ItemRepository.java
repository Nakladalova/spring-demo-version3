package com.example.springdemo.repository;

import com.example.springdemo.model.Item;
import com.example.springdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {

    @Query(value ="SELECT p.product_name, p.price, i.amount, i.item_id, p.product_id, s.shoppingcart_id FROM public.item i " +
            "INNER JOIN public.product p ON i.product_id= p.product_id " +
            "INNER JOIN public.shoppingcart s ON i.shoppingcart_id = s.shoppingcart_id " +
            "WHERE s.user_id = ?1", nativeQuery = true)
    Collection<Item> findAllItems(int user_id);


}
