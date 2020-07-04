package com.tampro.appbanhang.model;

public class Category {
    public int id;
    public String name;
    public String images;

    public Category() {

    }
    public Category(int id, String name, String images) {
        this.id = id;
        this.name = name;
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
