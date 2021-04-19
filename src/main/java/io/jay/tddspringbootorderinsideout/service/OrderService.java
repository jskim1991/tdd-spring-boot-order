package io.jay.tddspringbootorderinsideout.service;

import io.jay.tddspringbootorderinsideout.domain.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAll();
}
