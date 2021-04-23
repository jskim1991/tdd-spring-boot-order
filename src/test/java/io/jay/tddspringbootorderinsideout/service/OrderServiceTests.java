package io.jay.tddspringbootorderinsideout.service;

import io.jay.tddspringbootorderinsideout.domain.Order;
import io.jay.tddspringbootorderinsideout.doubles.FakeOrderJpaRepository;
import io.jay.tddspringbootorderinsideout.share.NameValue;
import io.jay.tddspringbootorderinsideout.share.NameValueList;
import io.jay.tddspringbootorderinsideout.store.OrderJpaStore;
import io.jay.tddspringbootorderinsideout.store.OrderStore;
import io.jay.tddspringbootorderinsideout.store.exception.NoSuchOrderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderServiceTests {

    private OrderStore orderStore;
    private OrderLogic orderLogic;

    @BeforeEach
    void setUp() {
        orderStore = new OrderJpaStore(new FakeOrderJpaRepository());
        orderLogic = new OrderLogic(orderStore);
    }

    @Test
    void test_getAllOrders_returnsList() {
        orderStore.addOrder(Order.builder().price(100).build());
        orderStore.addOrder(Order.builder().price(200).build());


        List<Order> orders = orderLogic.getAll();


        assertThat(orders.size(), equalTo(2));
    }

    @Test
    void test_getOrder_returnsOrder() {
        Order savedOrder = orderStore.addOrder(Order.builder().price(100).build());


        Order order = orderLogic.getOrder(savedOrder.getId());


        assertThat(order.getId(), equalTo(savedOrder.getId()));
        assertThat(order.getPrice(), equalTo(100));
    }

    @Test
    void test_getOrderWhenEmpty_throwsException() {
        assertThrows(NoSuchOrderException.class,
                () -> orderLogic.getOrder("999"));
    }

    @Test
    void test_addOrder_returnsOrder() {
        Order order = orderLogic.addOrder(Order.builder().price(100).build());


        assertThat(order.getId(), notNullValue());
        assertThat(order.getPrice(), equalTo(100));
    }

    @Test
    void test_updateOrder_returnsUpdatedOrder() {
        Order savedOrder = orderStore.addOrder(Order.builder().build());
        NameValueList nameValueList = new NameValueList();
        nameValueList.add(new NameValue("price", "500"));

        Order updatedOrder = orderLogic.updateOrder(savedOrder.getId(), nameValueList);


        assertThat(updatedOrder.getId(), equalTo(savedOrder.getId()));
        assertThat(updatedOrder.getPrice(), equalTo(500));
    }

    @Test
    void test_updateOrderWhenEmpty_throwsException() {
        assertThrows(NoSuchOrderException.class,
                () -> orderLogic.updateOrder("999", new NameValueList()));
    }

    @Test
    void test_deleteOrder_deletesOrder() {
        Order order = orderStore.addOrder(Order.builder().build());


        orderLogic.deleteOrder(order.getId());


        assertThat(orderLogic.getAll().isEmpty(), equalTo(true));
    }

    @Test
    void  test_deleteOrderWhenEmpty_throwsException() {
        assertThrows(NoSuchOrderException.class, () -> orderLogic.deleteOrder("999"));
    }
}
