package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.model.Restaurant;
import com.example.demo.pojo.MenuItem;
import com.example.demo.pojo.OrderItem;
import com.example.demo.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {
    private RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public void add(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    public void updateMenu(String name, List<MenuItem> newItems) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(name);
        if (restaurant.isPresent()) {
            List<MenuItem> currentMenu = restaurant.get().getMenu();
            for (MenuItem newItem : newItems) {
                boolean found = false;
                for (MenuItem item : currentMenu) {
                    if (item.getName().equals(newItem.getName())) {
                        item.setPrice(newItem.getPrice());
                        found = true;
                        break;
                    }
                }
                if (!found) currentMenu.add(newItem);
            }
            restaurantRepository.save(restaurant.get());
        }

    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }


    public boolean canFulfillOrder(Restaurant restaurant, List<OrderItem> items) {
        //checks whether the restaurant have all the items that are ordered
        List<String> menuNames = restaurant.getMenu().stream().map(MenuItem::getName).toList();
        return items.stream().allMatch(item -> menuNames.contains(item.getName()));
    }

    public int calculateBill(Restaurant restaurant, List<OrderItem> items) {
        int total = 0;
        for (OrderItem item : items) {
            for (MenuItem menuItem : restaurant.getMenu()) {
                if (menuItem.getName().equals(item.getName())) {
                    total += menuItem.getPrice() * item.getQuantity();
                    break;
                }
            }
        }
        return total;
    }

    public boolean canAcceptOrder(Restaurant restaurant) {
        //checks the restaurant max order capacity
        return restaurant.getCurrentOrders().size() < restaurant.getMaxOrders();
    }

    public void addOrderToRestaurant(Restaurant restaurant, Order order) {
        // adds order to the selected restaurant
        List<Integer> current = new ArrayList<>(restaurant.getCurrentOrders());
        current.add(order.getId());
        restaurant.setCurrentOrders(current);
        restaurantRepository.save(restaurant);
    }

    public void removeOrderFromRestaurant(String restaurantName, Order order) {
        // needed for removal of completed order from restaurant current list, so that new order can be added
        Restaurant restaurant=restaurantRepository.findById(restaurantName).orElse(null);
//        System.out.println(restaurant.getCurrentOrders());
//        System.out.println(restaurant.getMaxOrders());
//        System.out.println(restaurant.getName());
        List<Integer> current = new ArrayList<>(restaurant.getCurrentOrders());
        current.removeIf(o -> o.equals(order.getId()));
        restaurant.setCurrentOrders(current);
        restaurantRepository.save(restaurant);
    }

    public Restaurant selectRestaurantByLowestCost(List<Restaurant> restaurants, List<OrderItem> items) {
        //lowest strategy
        return restaurants.stream()
                .filter(r -> canAcceptOrder(r) && canFulfillOrder(r, items))
                .min(Comparator.comparingInt(r -> calculateBill(r, items)))
                .orElse(null);
    }

}
