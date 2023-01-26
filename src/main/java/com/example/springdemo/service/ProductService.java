package com.example.springdemo.service;

import com.example.springdemo.model.Item;
import com.example.springdemo.model.Product;
import com.example.springdemo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    public void  saveProductToDB(MultipartFile file, String name, String description, int price)
    {
        Product product = new Product();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        /*if(fileName.contains(".."))
        {
            System.out.println("not a a valid file");
        } */
        try {
            product.setProduct_photo((Base64.getEncoder().encodeToString(file.getBytes())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        product.setProduct_id(productRepo.findMaxID()+5);
        product.setDescription(description);
        product.setProduct_name(name);
        product.setPrice(price);

        productRepo.save(product);
    }

    public List<Product> getAllProduct()
    {
        return productRepo.findAll();
    }

    public Product getProduct(int id){
        return productRepo.findProductByID(id);
    }

    public int getProductByName(String name){
        return productRepo.findProductByName(name);
    }
}
