package io.jay.tddspringbootorderinsideout.store.jpa;

import io.jay.tddspringbootorderinsideout.store.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, String> {
}
