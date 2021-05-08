package io.jay.tddspringbootorderinsideout.item.store;

import io.jay.tddspringbootorderinsideout.item.store.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemJpaRepository extends JpaRepository<ItemEntity, String> {
}
