package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.model.Restaurant;
import com.example.demo.pojo.MenuItem;
import com.example.demo.pojo.OrderItem;
import com.example.demo.service.OrderService;
import com.example.demo.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Controller java file
@RestController
@RequestMapping("/api")
public class FoodOrderingController {
    private RestaurantService restaurantService;
    private OrderService orderService;

    //constructor injection
    @Autowired
    public FoodOrderingController(RestaurantService restaurantService, OrderService orderService) {
        this.restaurantService = restaurantService;
        this.orderService = orderService;
    }

    //home page for health check
    @GetMapping("/home")
    public ResponseEntity<String> home(){
        return ResponseEntity.ok("hi");
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<Restaurant>> getRestaurants(){
        List<Restaurant> restaurants = restaurantService.getAll();
        return ResponseEntity.ok(restaurants);
    }


    @PostMapping("/restaurants")
    public ResponseEntity<String> onboardRestaurant(@RequestBody Restaurant restaurant) {
        //Validating request body
        if (restaurant.getName() == null || restaurant.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Restaurant name is required");
        }

        if (restaurant.getRating() == null){
            return ResponseEntity.badRequest().body("Rating is not valid");
        }

        if(restaurant.getMaxOrders() == null){
            return ResponseEntity.badRequest().body("Max Orders is required");
        }

        restaurantService.add(restaurant);
        return ResponseEntity.ok("Restaurant onboarded successfully");
    }

    @PutMapping("/restaurants/{name}/menu")
    public ResponseEntity<String> updateMenu(@PathVariable String name, @RequestBody List<MenuItem> newItems) {
        // Validate restaurant existence
        if (restaurantService.getAll().stream().noneMatch(r -> r.getName().equalsIgnoreCase(name))) {
            return ResponseEntity.badRequest().body("Restaurant with name '" + name + "' not found");
        }
        restaurantService.updateMenu(name, newItems);
        return ResponseEntity.ok("Menu updated successfully");
    }

    @PostMapping("/orders")
    public ResponseEntity<String> placeOrder(@RequestParam String user,
                                             @RequestParam String strategy,
                                             @RequestBody List<OrderItem> items) {
        //Validating request body
        if (user == null || user.isEmpty()) {
            return ResponseEntity.badRequest().body("User is required");
        }
        if (!strategy.equalsIgnoreCase("lowest_cost") && !strategy.equalsIgnoreCase("highest_rating")) {
            return ResponseEntity.badRequest().body("Invalid strategy. Use 'lowest_cost' or 'highest_rating'");
        }
        if (items == null || items.isEmpty()) {
            return ResponseEntity.badRequest().body("Order items cannot be empty");
        }
        String response = orderService.placeOrder(user, items, strategy);
        if (response.startsWith("Cannot")) {
            return ResponseEntity.status(400).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @PutMapping("/orders/{id}/complete")
    //to mark an order as complete
    public ResponseEntity<String> completeOrder(@PathVariable Integer id) {
        String result = orderService.completeOrder(id);
        if (result.contains("Invalid")) {
            return ResponseEntity.status(400).body(result);
        }
        return ResponseEntity.ok(result);
    }

}

