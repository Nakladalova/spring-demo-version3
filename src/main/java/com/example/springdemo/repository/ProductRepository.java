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

    @Query("SELECT p FROM Product p WHERE p.product_id = ?1")
    public Product findProductByID(int id);

    @Query("SELECT p.product_id FROM Product p WHERE p.product_name = ?1")
    public int findProductByName(String product_name);

}

