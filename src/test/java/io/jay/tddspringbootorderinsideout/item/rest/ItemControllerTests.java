package io.jay.tddspringbootorderinsideout.item.rest;

import io.jay.tddspringbootorderinsideout.item.domain.Item;
import io.jay.tddspringbootorderinsideout.item.rest.ItemController;
import io.jay.tddspringbootorderinsideout.item.store.ItemStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ItemControllerTests {

    private ItemStore mockItemStore;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockItemStore = mock(ItemStore.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ItemController(mockItemStore))
                .build();
    }

    @Test
    void test_getAllItems_returnsOk() throws Exception {
        mockMvc.perform(get("/items"))
                .andExpect(status().isOk());
    }

    @Test
    void test_getAllItems_returnsItems() throws Exception {
        when(mockItemStore.getAll())
                .thenReturn(Collections.singletonList(new Item("blue chair")));


        mockMvc.perform(get("/items"))
                .andExpect(jsonPath("$[0].name", equalTo("blue chair")))
        ;
    }
}
