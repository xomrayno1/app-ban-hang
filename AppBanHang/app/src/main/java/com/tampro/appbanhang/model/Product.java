package com.tampro.appbanhang.model;

import java.io.Serializable;

public class Product implements Serializable {
    public int Id;
    public String name;
    public int price;
    public String images;
    public int IdCategory;
    public String description;

    public Product() {

    }
    public Product(int id, String name, int price, String images, int idCategory, String description) {
        Id = id;
        this.name = name;
        this.price = price;
        this.images = images;
        IdCategory = idCategory;
        this.description = description;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getIdCategory() {
        return IdCategory;
    }

    public void setIdCategory(int idCategory) {
        IdCategory = idCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
