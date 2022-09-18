package com.example.foodstore;

public class Delivery {

    String address, apartment, entrance, floor;

    public Delivery(){}

    public Delivery(String address, String apartment, String entrance, String floor) {
        this.address = address;
        this.apartment = apartment;
        this.entrance = entrance;
        this.floor = floor;
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
}
