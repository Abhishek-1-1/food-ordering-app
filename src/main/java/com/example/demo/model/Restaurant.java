package com.example.demo.model;

import com.example.demo.pojo.MenuItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "resturants")
public class Restaurant {
    @Id
    private String name;
    private Double rating;
    private Integer maxOrders;
    private List<MenuItem> menu = new ArrayList<>();

//    @JsonIgnore
    private List<Integer> currentOrders = new ArrayList<>();

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

    public List<Integer> getCurrentOrders() {
        return currentOrders;
    }

    public void setCurrentOrders(List<Integer> currentOrders) {
        this.currentOrders = currentOrders;
    }
}
