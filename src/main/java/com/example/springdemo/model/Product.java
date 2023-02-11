package com.example.springdemo.model;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    public Integer getProductId() {
        return productId;
    }

    @Column(name = "description")
    private String description;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private Integer price;

    public Integer getProductId(int maxID) {
        return productId;
    }

    public void setProductId(Integer product_id) {
        this.productId = product_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String product_name) {
        this.productName = product_name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }


}
