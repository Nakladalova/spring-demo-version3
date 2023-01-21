package com.example.springdemo.repository;

import com.example.springdemo.model.Item;
import com.example.springdemo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value ="SELECT MAX(product_id)" +
            "FROM public.product", nativeQuery = true)
    int findMaxID();

}

