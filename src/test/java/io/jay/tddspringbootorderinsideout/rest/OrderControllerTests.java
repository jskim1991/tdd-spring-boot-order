package io.jay.tddspringbootorderinsideout.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jay.tddspringbootorderinsideout.domain.Order;
import io.jay.tddspringbootorderinsideout.service.OrderService;
import io.jay.tddspringbootorderinsideout.share.NameValue;
import io.jay.tddspringbootorderinsideout.share.NameValueList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderControllerTests {

    private OrderService mockOrderService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockOrderService = Mockito.mock(OrderService.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new OrderController(mockOrderService))
                .build();
    }

    @Test
    void test_getAll_returnsOk() throws Exception {
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk());
    }

    @Test
    void test_getAll_returnsListOfOrders() throws Exception {
        when(mockOrderService.getAll())
                .thenReturn(Collections.singletonList(
                        Order.builder().price(100).build()
                ));


        mockMvc.perform(get("/orders"))
                .andExpect(jsonPath("$[0].id", notNullValue()))
                .andExpect(jsonPath("$[0].price", equalTo(100)))
        ;
    }

    @Test
    void test_getOrder_returnsOrder() throws Exception {
        when(mockOrderService.getOrder("1"))
                .thenReturn(Order.builder().price(100).build());


        mockMvc.perform(get("/orders/1"))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.price", equalTo(100)))
        ;
    }

    @Test
    void test_getOrder_usesCorrectInput() throws Exception {
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);


        mockMvc.perform(get("/orders/999"));


        verify(mockOrderService, times(1)).getOrder(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue(), equalTo("999"));
    }

    @Test
    void test_addOrder_returnsOrder() throws Exception {
        when(mockOrderService.addOrder(any(Order.class)))
                .thenReturn(Order.builder().price(100).build());




        mockMvc.perform(post("/orders")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.price", equalTo(100)))
        ;
    }

    @Test
    void test_addOrder_usesCorrectBody() throws Exception {
        ArgumentCaptor<Order> argumentCaptor = ArgumentCaptor.forClass(Order.class);


        mockMvc.perform(post("/orders")
                .content(new ObjectMapper().writeValueAsString(Order.builder().price(100).build()))
                .contentType(MediaType.APPLICATION_JSON));


        verify(mockOrderService).addOrder(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getPrice(), equalTo(100));
    }

    @Test
    void test_updateOrder_returnsOk() throws Exception {
        mockMvc.perform(patch("/orders/999")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void test_updateOrder_usesCorrectInputs() throws Exception {
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<NameValueList> updateFieldsCaptor = ArgumentCaptor.forClass(NameValueList.class);

        NameValueList nameValueList = new NameValueList();
        nameValueList.add(new NameValue("price", "200"));


        mockMvc.perform(patch("/orders/1")
                .content(new ObjectMapper().writeValueAsString(nameValueList))
                .contentType(MediaType.APPLICATION_JSON));


        verify(mockOrderService).updateOrder(idCaptor.capture(), updateFieldsCaptor.capture());
        assertThat(idCaptor.getValue(), equalTo("1"));
        assertThat(updateFieldsCaptor.getValue().getNameValues().get(0).getValue(), equalTo("200"));
        assertThat(updateFieldsCaptor.getValue().getNameValues().get(0).getName(), equalTo("price"));
    }

    @Test
    void test_updateOrder_returnsOrder() throws Exception {
        when(mockOrderService.updateOrder(any(), any()))
                .thenReturn(Order.builder().price(100).build());

        mockMvc.perform(patch("/orders/1")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.price", equalTo(100)))
        ;
    }

    @Test
    void test_delete_returnsOk() throws Exception {
        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void test_delete_usesCorrectId() throws Exception {
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);


        mockMvc.perform(delete("/orders/1"));


        verify(mockOrderService).deleteOrder(idCaptor.capture());
        assertThat(idCaptor.getValue(), equalTo("1"));
    }
}
