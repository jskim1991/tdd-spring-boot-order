package io.jay.tddspringbootorderinsideout.item.store;

import io.jay.tddspringbootorderinsideout.item.domain.Item;

import java.util.List;

public interface ItemStore {

    List<Item> getAll();
}
