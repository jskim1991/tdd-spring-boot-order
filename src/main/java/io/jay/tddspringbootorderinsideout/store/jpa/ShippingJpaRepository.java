package io.jay.tddspringbootorderinsideout.store.jpa;

import io.jay.tddspringbootorderinsideout.store.entity.ShippingAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingJpaRepository extends JpaRepository<ShippingAddressEntity, String> {
}
