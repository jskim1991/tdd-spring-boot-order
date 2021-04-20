package io.jay.tddspringbootorderinsideout.store;

import io.jay.tddspringbootorderinsideout.domain.Order;
import io.jay.tddspringbootorderinsideout.store.exception.NoSuchOrderException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OrderJpaStore implements OrderStore {

    private JpaRepository<OrderEntity, String> jpaRepository;

    public OrderJpaStore(JpaRepository<OrderEntity, String> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }


    @Override
    public List<Order> getAllOrders() {
        List<OrderEntity> orders = jpaRepository.findAll();
        return orders.stream()
                .map(OrderEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Order getOrder(String id) {
        OrderEntity orderEntity = jpaRepository.findById(id).orElseThrow(
                () -> new NoSuchOrderException("No such order for id " + id));
        return orderEntity.toDomain();
    }

    @Override
    public Order addOrder(Order order) {
        return jpaRepository.save(new OrderEntity(order)).toDomain();
    }

    @Override
    public Order updateOrder(Order order) {
        return jpaRepository.save(new OrderEntity(order)).toDomain();
    }

    @Override
    public void deleteOrder(String id) {
        jpaRepository.deleteById(id);
    }
}
