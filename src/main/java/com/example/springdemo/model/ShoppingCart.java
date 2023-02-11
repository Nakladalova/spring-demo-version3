package com.example.springdemo.model;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Id;

@Entity
@Table(name = "shoppingcart")
public class ShoppingCart {

    @Id
    @Column(name = "shoppingcart_id")
    private Integer shoppingcartId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "wallet")
    private Integer wallet;

    @Column(name = "total")
    private Integer total;

    public Integer getShoppingcartId() {
        return shoppingcartId;
    }

    public void setShoppingcartId(Integer shoppingcart_id) {
        this.shoppingcartId = shoppingcart_id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer user_id) {
        this.userId = user_id;
    }

    public Integer getWallet() {
        return wallet;
    }

    public void setWallet(Integer wallet) {
        this.wallet = wallet;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
