package com.example.foodstore.categoryList;

public class Category {

    private int id;
    private String title;
    private int imagePath;

    public Category(int id, String title, int imagePath){
        this.id = id;
        this.title = title;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getImagePath() {
        return imagePath;
    }
}
