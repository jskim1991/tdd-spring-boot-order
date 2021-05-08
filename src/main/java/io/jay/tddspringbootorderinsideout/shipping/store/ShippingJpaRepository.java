package io.jay.tddspringbootorderinsideout.shipping.store;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingJpaRepository extends JpaRepository<ShippingAddressEntity, String> {
}
