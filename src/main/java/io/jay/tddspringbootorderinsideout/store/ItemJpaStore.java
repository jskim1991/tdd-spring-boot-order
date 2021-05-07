package io.jay.tddspringbootorderinsideout.store;

import io.jay.tddspringbootorderinsideout.domain.Item;
import io.jay.tddspringbootorderinsideout.store.jpa.ItemJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ItemJpaStore implements ItemRepository {

    private final ItemJpaRepository itemJpaRepository;

    public ItemJpaStore(ItemJpaRepository itemJpaRepository) {
        this.itemJpaRepository = itemJpaRepository;
    }

    @Override
    public List<Item> getAll() {
        return itemJpaRepository.findAll()
                .stream()
                .map(ItemEntity::toDomain)
                .collect(Collectors.toList());
    }
}
