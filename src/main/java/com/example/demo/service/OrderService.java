package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.model.Restaurant;
import com.example.demo.pojo.OrderItem;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private RestaurantRepository restaurantRepository;
    private RestaurantService restaurantService;

    @Autowired
    public OrderService(OrderRepository orderRepository, RestaurantRepository restaurantRepository, RestaurantService restaurantService) {
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
        this.restaurantService = restaurantService;
    }

    public String placeOrder(String user, List<OrderItem> items, String strategy) {
        Restaurant restaurant;
        if ("lowest_cost".equalsIgnoreCase(strategy)) {
            restaurant = restaurantService.selectRestaurantByLowestCost(restaurantRepository.findAll(), items);
        } else {
            return "Cannot assign the order, only lowest cost is available right now";
        }

        if (restaurant == null) {
            return "Cannot assign the order";
        }

        Order order = new Order(user, items);
        order.setRestaurantName(restaurant.getName());
        restaurantService.addOrderToRestaurant(restaurant, order);
        orderRepository.save(order);
        return "Order " + order.getId() + " assigned to " + restaurant.getName();
    }


    public String completeOrder(Integer orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent() && "ACCEPTED".equals(order.get().getStatus())) {
            order.get().setStatus("COMPLETED");
            orderRepository.save(order.get());
            restaurantService.removeOrderFromRestaurant(order.get().getRestaurantName(), order.orElse(null));
            return "Order " + orderId + " marked as COMPLETED";
        } else {
            return "Invalid Order ID or already completed";
        }
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }
}
