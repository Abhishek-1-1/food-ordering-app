package com.example.demo.repository;

import com.example.demo.model.Restaurant;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RestaurantRepository {
    private final List<Restaurant> restaurants = new ArrayList<>();

    public void save(Restaurant restaurant) {
        restaurants.removeIf(r -> r.getName().equalsIgnoreCase(restaurant.getName()));
        restaurants.add(restaurant);
    }

    public Restaurant findByName(String name) {
        return restaurants.stream().filter(r -> r.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }

    public List<Restaurant> findAll() {
        return restaurants;
    }
}