package io.jay.tddspringbootorderinsideout.order.store.repository;

import io.jay.tddspringbootorderinsideout.order.domain.Order;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OrderEntity {

    @Id
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

    public OrderEntity() {
        
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
