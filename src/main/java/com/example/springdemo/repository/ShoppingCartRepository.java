package com.example.springdemo.repository;

import com.example.springdemo.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer>, CrudRepository<ShoppingCart, Integer> {
    @Query("SELECT s FROM ShoppingCart s WHERE s.user_id = ?1")
    public ShoppingCart findShoppingCartByUser_id(int user_id);

    @Query("UPDATE ShoppingCart s SET s.total = ?1 WHERE s.user_id = ?2")
    @Modifying
    public void updateTotal(int total, int user_id);

}
