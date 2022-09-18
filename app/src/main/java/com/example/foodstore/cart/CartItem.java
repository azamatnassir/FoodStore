package com.example.foodstore.cart;

public class CartItem {

    private String title;
    private String description;
    private int quantity;
    private int price;
    private int total;
    private String imagePath;

    public CartItem(){}

    public CartItem(String title, String imagePath, int price, int quantity,  String description, int total){
        this.title = title;
        this.imagePath = imagePath;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getDescription() {
        return description;
    }

    public int getTotal() {
        return total;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
