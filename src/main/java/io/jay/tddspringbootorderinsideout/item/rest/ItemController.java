package io.jay.tddspringbootorderinsideout.item.rest;

import io.jay.tddspringbootorderinsideout.item.domain.Item;
import io.jay.tddspringbootorderinsideout.item.store.ItemStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {

    private final ItemStore itemStore;

    public ItemController(ItemStore itemStore) {
        this.itemStore = itemStore;
    }

    @GetMapping("/items")
    public List<Item> getAllItems() {
        return itemStore.getAll();
    }
}
