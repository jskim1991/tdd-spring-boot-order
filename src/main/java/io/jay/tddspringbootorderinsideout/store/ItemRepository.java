package io.jay.tddspringbootorderinsideout.store;

import io.jay.tddspringbootorderinsideout.domain.Item;

import java.util.List;

public interface ItemRepository {

    List<Item> getAll();
}
