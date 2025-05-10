package com.orders.orderService;

import com.orders.orderService.model.Product;
import com.orders.orderService.model.User;

public class OrderDetailsResponse {
    private Order order;
    private User user;
    private Product product;

    public OrderDetailsResponse(Order order, User user, Product product) {
        this.order = order;
        this.user = user;
        this.product = product;
    }

    public Order getOrder() { return order; }
    public User getUser() { return user; }
    public Product getProduct() { return product; }
}
