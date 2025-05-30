package com.example.demo.model;

import com.example.demo.pojo.MenuItem;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private Double rating;
    private Integer maxOrders;
    private List<MenuItem> menu = new ArrayList<>();

    @JsonIgnore
    private List<Order> currentOrders = new ArrayList<>();

    public Restaurant() {}

    public Restaurant(String name, Double rating, Integer maxOrders) {
        this.name = name;
        this.rating = rating;
        this.maxOrders = maxOrders;
    }

    public String getName() {
        return name;
    }

    public Double getRating() {
        return rating;
    }

    public Integer getMaxOrders() {
        return maxOrders;
    }

    public List<MenuItem> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuItem> menu) {
        this.menu = menu;
    }

    public List<Order> getCurrentOrders() {
        return currentOrders;
    }

    public void setCurrentOrders(List<Order> currentOrders) {
        this.currentOrders = currentOrders;
    }
}
