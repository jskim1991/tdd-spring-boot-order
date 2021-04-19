package io.jay.tddspringbootorderinsideout.order.store;

import io.jay.tddspringbootorderinsideout.order.domain.Order;
import io.jay.tddspringbootorderinsideout.share.NameValueList;

import java.util.List;

public interface OrderStore {
    List<Order> getAllOrders();

    Order getOrder(String id);

    Order addOrder(Order order);

    Order updateOrder(Order order);

    void deleteOrder(String id);
}
