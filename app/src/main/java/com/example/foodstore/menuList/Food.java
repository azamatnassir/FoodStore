package com.example.foodstore.menuList;

public class Food {

    private int id;
    private String title;
    private String description;
    private int price;
    private String imagePath;

    public Food(int id, String title, String imagePath, int price, String description){
        this.id = id;
        this.title = title;
        this.imagePath = imagePath;
        this.price = price;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getDescription() {
        return description;
    }
}
