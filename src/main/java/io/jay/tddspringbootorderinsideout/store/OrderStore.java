package io.jay.tddspringbootorderinsideout.store;

import io.jay.tddspringbootorderinsideout.domain.Order;
import io.jay.tddspringbootorderinsideout.share.NameValueList;

import java.util.List;

public interface OrderStore {
    List<Order> getAllOrders();

    Order getOrder(String id);

    Order addOrder(Order order);

    Order updateOrder(String id, NameValueList nameValueList);

    void deleteOrder(String id);
}
