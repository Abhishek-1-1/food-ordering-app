package com.example.demo.model;

import com.example.demo.pojo.OrderItem;

import java.util.List;

public class Order {
    private static Integer ID_COUNTER = 1;
    private Integer id;
    private String user;
    private List<OrderItem> items;
    private Restaurant restaurant;
    private String status;

    public Order() {}

    public Order(String user, List<OrderItem> items) {
        this.id = ID_COUNTER++;
        this.user = user;
        this.items = items;
        this.status = "ACCEPTED";
    }

    public static Integer getIdCounter() {
        return ID_COUNTER;
    }

    public static void setIdCounter(Integer idCounter) {
        ID_COUNTER = idCounter;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public String getStatus() {
        return status;
    }
}
