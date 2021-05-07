package io.jay.tddspringbootorderinsideout.store.jpa;

import io.jay.tddspringbootorderinsideout.store.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemJpaRepository extends JpaRepository<ItemEntity, String> {
}
