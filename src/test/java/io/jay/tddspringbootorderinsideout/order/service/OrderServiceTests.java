package io.jay.tddspringbootorderinsideout.order.service;

import io.jay.tddspringbootorderinsideout.order.domain.Order;
import io.jay.tddspringbootorderinsideout.order.store.FakeOrderJpaRepository;
import io.jay.tddspringbootorderinsideout.share.domain.NameValue;
import io.jay.tddspringbootorderinsideout.share.domain.NameValueList;
import io.jay.tddspringbootorderinsideout.order.store.OrderJpaStore;
import io.jay.tddspringbootorderinsideout.order.store.OrderStore;
import io.jay.tddspringbootorderinsideout.order.exception.NoSuchOrderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderServiceTests {

    private OrderStore orderStore;
    private DefaultOrderService defaultOrderService;

    @BeforeEach
    void setUp() {
        orderStore = new OrderJpaStore(new FakeOrderJpaRepository());
        defaultOrderService = new DefaultOrderService(orderStore);
    }

    @Test
    void test_getAllOrders_returnsList() {
        orderStore.addOrder(Order.builder().price(100).build());
        orderStore.addOrder(Order.builder().price(200).build());


        List<Order> orders = defaultOrderService.getAll();


        assertThat(orders.size(), equalTo(2));
    }

    @Test
    void test_getOrder_returnsOrder() {
        Order savedOrder = orderStore.addOrder(Order.builder().price(100).build());


        Order order = defaultOrderService.getOrder(savedOrder.getId());


        assertThat(order.getId(), equalTo(savedOrder.getId()));
        assertThat(order.getPrice(), equalTo(100));
    }

    @Test
    void test_getOrderWhenEmpty_throwsException() {
        assertThrows(NoSuchOrderException.class,
                () -> defaultOrderService.getOrder("999"));
    }

    @Test
    void test_addOrder_returnsOrder() {
        Order order = defaultOrderService.addOrder(Order.builder().price(100).build());


        assertThat(order.getId(), notNullValue());
        assertThat(order.getPrice(), equalTo(100));
    }

    @Test
    void test_updateOrder_returnsUpdatedOrder() {
        Order savedOrder = orderStore.addOrder(Order.builder().build());
        NameValueList nameValueList = new NameValueList();
        nameValueList.add(new NameValue("price", "500"));


        Order updatedOrder = defaultOrderService.updateOrder(savedOrder.getId(), nameValueList);


        assertThat(updatedOrder.getId(), equalTo(savedOrder.getId()));
        assertThat(updatedOrder.getPrice(), equalTo(500));
    }

    @Test
    void test_updateOrderWhenEmpty_throwsException() {
        assertThrows(NoSuchOrderException.class,
                () -> defaultOrderService.updateOrder("999", new NameValueList()));
    }

    @Test
    void test_deleteOrder_deletesOrder() {
        Order order = orderStore.addOrder(Order.builder().build());


        defaultOrderService.deleteOrder(order.getId());


        assertThat(defaultOrderService.getAll().isEmpty(), equalTo(true));
    }

    @Test
    void test_deleteOrderWhenEmpty_throwsException() {
        assertThrows(NoSuchOrderException.class, () -> defaultOrderService.deleteOrder("999"));
    }
}
