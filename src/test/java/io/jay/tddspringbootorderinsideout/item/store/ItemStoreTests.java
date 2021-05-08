package io.jay.tddspringbootorderinsideout.item.store;

import io.jay.tddspringbootorderinsideout.item.domain.Item;
import io.jay.tddspringbootorderinsideout.item.store.ItemEntity;
import io.jay.tddspringbootorderinsideout.item.store.ItemJpaStore;
import io.jay.tddspringbootorderinsideout.item.store.ItemStore;
import io.jay.tddspringbootorderinsideout.item.store.ItemJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemStoreTests {

    private ItemJpaRepository mockItemJpaRepository;
    private ItemStore itemJpaStore;

    @BeforeEach
    void setUp() {
        mockItemJpaRepository = mock(ItemJpaRepository.class);
        itemJpaStore = new ItemJpaStore(mockItemJpaRepository);
    }

    @Test
    void test_getAll_returnsItems() {
        when(mockItemJpaRepository.findAll())
                .thenReturn(Collections.singletonList(new ItemEntity(new Item("green chair"))));


        List<Item> items = itemJpaStore.getAll();


        assertThat(items.get(0).getName(), equalTo("green chair"));
    }
}
