package com.example.springdemo.model;

import javax.persistence.*;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer item_id;

    @Column(name = "shoppingcart_id")
    private long shoppingcart_id;

    @Column(name = "product_id")
    private Integer product_id;

    @Column(name = "amount", nullable = false, precision=11, scale=0)
    private Integer amount;

    private String product_name;

    private Integer price;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public long getShoppingcart_id() {
        return shoppingcart_id;
    }

    public void setShoppingcart_id(long shoppingcart_id) {
        this.shoppingcart_id = shoppingcart_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
