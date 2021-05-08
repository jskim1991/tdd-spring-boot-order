package io.jay.tddspringbootorderinsideout.item.store;

import io.jay.tddspringbootorderinsideout.item.domain.Item;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ItemJpaStore implements ItemStore {

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
