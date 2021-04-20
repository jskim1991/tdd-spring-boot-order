package io.jay.tddspringbootorderinsideout.store;

import io.jay.tddspringbootorderinsideout.domain.Order;

import java.util.List;

public interface OrderStore {
    List<Order> getAllOrders();

    Order getOrder(String id);

    Order addOrder(Order order);

    Order updateOrder(Order order);

    void deleteOrder(String id);
}
