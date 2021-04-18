package io.jay.tddspringbootorderinsideout.store.repository;

import io.jay.tddspringbootorderinsideout.domain.Order;

public class OrderEntity {

    private String id;
    private Integer price;

    public OrderEntity(String id, Integer price) {
        this.id = id;
        this.price = price;
    }

    public OrderEntity(Order order) {
        this.id = order.getId();
        this.price = order.getPrice();
    }

    public String getId() {
        return id;
    }

    public Integer getPrice() {
        return price;
    }

    public Order toDomain() {
        return new Order(this.id, this.price);
    }
}
