package com.example.springdemo.model;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer product_id;

    @Column(name = "product_photo")
    private String product_photo;

    public Integer getProduct_id() {
        return product_id;
    }

    @Column(name = "description")
    private String description;

    @Column(name = "product_name")
    private String product_name;

    @Column(name = "price")
    private Integer price;

    public String getPhotos_image_path() {
        if (product_photo == null || product_id == null) return null;

        return "/product-photos/" + product_id + "/" + product_photo;
    }

    public void setPhotos_image_path(String photos_image_path) {
        this.photos_image_path = photos_image_path;
    }

    @Column(name = "photos_image_path")
    private String photos_image_path;

    public Integer getProduct_id(int maxID) {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getProduct_photo() {
        return product_photo;
    }

    public void setProduct_photo(String product_photo) {
        this.product_photo = product_photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    @Transient
    public String getPhotosImagePath() {
        if (product_photo == null || product_id == null) return null;

        return "/product-photos/" + product_id + "/" + product_photo;
    }


}
