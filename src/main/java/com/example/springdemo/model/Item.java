package com.example.springdemo.model;

import javax.persistence.*;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer itemId;

    @Column(name = "shoppingcart_id")
    private long shoppingcartId;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "amount", nullable = false, precision=11, scale=0)
    private Integer amount;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private Integer price;

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

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer item_id) {
        this.itemId = item_id;
    }

    public long getShoppingcartId() {
        return shoppingcartId;
    }

    public void setShoppingcartId(long shoppingcart_id) {
        this.shoppingcartId = shoppingcart_id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer product_id) {
        this.productId = product_id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
