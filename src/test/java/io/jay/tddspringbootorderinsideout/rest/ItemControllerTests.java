package io.jay.tddspringbootorderinsideout.rest;

import io.jay.tddspringbootorderinsideout.domain.Item;
import io.jay.tddspringbootorderinsideout.store.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

    private ItemRepository mockItemRepository;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockItemRepository = mock(ItemRepository.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ItemController(mockItemRepository))
                .build();
    }

    @Test
    void test_getAllItems_returnsOk() throws Exception {
        mockMvc.perform(get("/items"))
                .andExpect(status().isOk());
    }

    @Test
    void test_getAllItems_returnsItems() throws Exception {
        when(mockItemRepository.getAll())
                .thenReturn(Collections.singletonList(new Item("blue chair")));


        mockMvc.perform(get("/items"))
                .andExpect(jsonPath("$[0].name", equalTo("blue chair")))
        ;
    }
}
