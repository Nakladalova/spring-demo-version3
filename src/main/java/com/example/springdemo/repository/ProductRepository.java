package com.example.springdemo.repository;

import com.example.springdemo.model.Item;
import com.example.springdemo.model.Product;
import com.example.springdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value ="SELECT MAX(product_id)" +
            "FROM public.product", nativeQuery = true)
    public int findMaxID();

    @Query("SELECT p FROM Product p WHERE p.productId = ?1")
    public Product findProductByID(int id);

    @Query("SELECT p.productId FROM Product p WHERE p.productName = ?1")
    public int findProductByName(String productName);

}

