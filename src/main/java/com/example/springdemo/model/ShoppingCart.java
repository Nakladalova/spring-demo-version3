package com.example.springdemo.model;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Id;

@Entity
@Table(name = "shoppingcart")
public class ShoppingCart {

    @Id
    @Column(name = "shoppingcart_id")
    private Integer shoppingcart_id;

    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "wallet")
    private Integer wallet;

    @Column(name = "total")
    private Integer total;

    public Integer getShoppingcart_id() {
        return shoppingcart_id;
    }

    public void setShoppingcart_id(Integer shoppingcart_id) {
        this.shoppingcart_id = shoppingcart_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
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
