package com.example.foodstore.history;

import com.example.foodstore.cart.CartItem;

import java.util.ArrayList;
import java.util.List;

public class HistoryItem {

    private String date;
    private String delivery;
    private String address;
    private String apartment;
    private String entrance;
    private String floor;
    private String status;
    private String payment;
    private int price;
    private ArrayList<CartItem> historyFoods;

    public HistoryItem(String date, String delivery, String address, String apartment, String entrance, String floor, String status, String payment, int price, ArrayList<CartItem> historyFoods) {
        this.date = date;
        this.delivery = delivery;
        this.address = address;
        this.apartment = apartment;
        this.entrance = entrance;
        this.floor = floor;
        this.status = status;
        this.payment = payment;
        this.price = price;
        this.historyFoods = historyFoods;
    }

    public HistoryItem() {}

    public ArrayList<CartItem> getHistoryFoods() {
        return historyFoods;
    }

    public void setHistoryFoods(ArrayList<CartItem> historyFoods) {
        this.historyFoods = historyFoods;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getEntrance() {
        return entrance;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
