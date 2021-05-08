package io.jay.tddspringbootorderinsideout.order.service;

import io.jay.tddspringbootorderinsideout.order.domain.Order;
import io.jay.tddspringbootorderinsideout.share.domain.NameValueList;

import java.util.List;

public interface OrderService {
    List<Order> getAll();

    Order getOrder(String id);

    Order updateOrder(String id, NameValueList nameValueList);

    void deleteOrder(String id);

    Order addOrder(Order build);
}
