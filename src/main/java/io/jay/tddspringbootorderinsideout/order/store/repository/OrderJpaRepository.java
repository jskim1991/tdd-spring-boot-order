package io.jay.tddspringbootorderinsideout.order.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, String> {
}
