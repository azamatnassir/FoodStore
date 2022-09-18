package com.example.foodstore;

public class User {

    private String name;
    private int amount;
    private int total;

    public User(){}

    public User(String name, int amount, int total){
        this.name = name;
        this.amount = amount;
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public int getTotal() {
        return total;
    }
}
