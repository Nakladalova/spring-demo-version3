package com.example.springdemo.service;

import com.example.springdemo.model.Product;
import com.example.springdemo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    public Product getProduct(int id){
        return productRepo.findProductByID(id);
    }

    public int getProductByName(String name){
        return productRepo.findProductByName(name);
    }
}
