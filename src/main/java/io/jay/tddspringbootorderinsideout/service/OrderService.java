package io.jay.tddspringbootorderinsideout.service;

import io.jay.tddspringbootorderinsideout.domain.Order;
import io.jay.tddspringbootorderinsideout.share.NameValueList;

import java.util.List;

public interface OrderService {
    List<Order> getAll();

    Order getOrder(String id);

    Order updateOrder(String id, NameValueList nameValueList);

    void deleteOrder(String id);
}
