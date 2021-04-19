package io.jay.tddspringbootorderinsideout.order.service;

import io.jay.tddspringbootorderinsideout.order.domain.Order;
import io.jay.tddspringbootorderinsideout.order.store.OrderStore;
import io.jay.tddspringbootorderinsideout.share.NameValueList;

import java.util.List;

public class OrderLogic implements OrderService {

    private OrderStore orderStore;

    public OrderLogic(OrderStore orderStore) {
        this.orderStore = orderStore;
    }

    @Override
    public List<Order> getAll() {
        return orderStore.getAllOrders();
    }

    @Override
    public Order getOrder(String id) {
        return orderStore.getOrder(id);
    }

    @Override
    public Order updateOrder(String id, NameValueList nameValueList) {
        Order order = orderStore.getOrder(id);
        order.setValues(nameValueList);
        return orderStore.updateOrder(order);
    }

    @Override
    public void deleteOrder(String id) {
        orderStore.getOrder(id);
        orderStore.deleteOrder(id);
    }
}
