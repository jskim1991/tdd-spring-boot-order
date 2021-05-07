package io.jay.tddspringbootorderinsideout.store;

import io.jay.tddspringbootorderinsideout.domain.Item;
import io.jay.tddspringbootorderinsideout.store.jpa.ItemJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemRepositoryTests {

    private ItemJpaRepository mockItemJpaRepository;
    private ItemRepository itemJpaStore;

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
