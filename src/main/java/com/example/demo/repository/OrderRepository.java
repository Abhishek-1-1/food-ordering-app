package com.example.demo.repository;

import com.example.demo.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {
    private List<Order> orders = new ArrayList<>();

    public void save(Order order) {
        orders.add(order);
    }

    public Order findById(Integer id) {
        return orders.stream().filter(o -> o.getId().equals(id)).findAny().orElse(null);
    }

    public List<Order> getAll() {
        return orders;
    }

}
