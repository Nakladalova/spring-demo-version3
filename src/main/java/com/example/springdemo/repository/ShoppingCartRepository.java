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
    @Query("SELECT s FROM ShoppingCart s WHERE s.userId = ?1")
    public ShoppingCart findShoppingCartByUser_id(int userId);

    @Query("UPDATE ShoppingCart s SET s.total = ?1 WHERE s.userId = ?2")
    @Modifying
    public void updateTotal(int total, int userId);

    @Query("UPDATE ShoppingCart s SET s.wallet = ?1 WHERE s.userId = ?2")
    @Modifying
    public void updateWallet(int wallet, int userId);

    @Query("UPDATE ShoppingCart s SET s.total = ?1, s.wallet=?2 WHERE s.userId = ?3")
    @Modifying
    public void updateShoppingCart(int total, int wallet, int userId);


}
