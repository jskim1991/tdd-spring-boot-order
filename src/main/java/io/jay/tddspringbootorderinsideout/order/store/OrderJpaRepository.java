package io.jay.tddspringbootorderinsideout.order.store;

import io.jay.tddspringbootorderinsideout.order.store.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, String> {
}
