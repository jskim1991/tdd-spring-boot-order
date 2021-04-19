package io.jay.tddspringbootorderinsideout.order.tests.service;

import io.jay.tddspringbootorderinsideout.order.domain.Order;
import io.jay.tddspringbootorderinsideout.order.service.OrderLogic;
import io.jay.tddspringbootorderinsideout.order.store.OrderJpaStore;
import io.jay.tddspringbootorderinsideout.order.store.OrderStore;
import io.jay.tddspringbootorderinsideout.order.store.exception.NoSuchOrderException;
import io.jay.tddspringbootorderinsideout.order.tests.doubles.FakeOrderJpaRepository;
import io.jay.tddspringbootorderinsideout.share.NameValue;
import io.jay.tddspringbootorderinsideout.share.NameValueList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
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
        orderStore.addOrder(new Order("123", 100));
        orderStore.addOrder(new Order("222", 200));


        List<Order> orders = orderLogic.getAll();


        assertThat(orders.get(0).getId(), equalTo("123"));
        assertThat(orders.get(0).getPrice(), equalTo(100));
        assertThat(orders.get(1).getId(), equalTo("222"));
        assertThat(orders.get(1).getPrice(), equalTo(200));
    }

    @Test
    void test_getOrder_returnsOrder() {
        orderStore.addOrder(new Order("111", 100));


        Order order = orderLogic.getOrder("111");


        assertThat(order.getId(), equalTo("111"));
        assertThat(order.getPrice(), equalTo(100));
    }

    @Test
    void test_getOrderWhenEmpty_throwsException() {
        assertThrows(NoSuchOrderException.class,
                () -> orderLogic.getOrder("999"));
    }

    @Test
    void test_updateOrder_returnsUpdatedOrder() {
        orderStore.addOrder(new Order("111", 100));
        NameValueList nameValueList = new NameValueList();
        nameValueList.add(new NameValue("price", "500"));

        Order updatedOrder = orderLogic.updateOrder("111", nameValueList);


        assertThat(updatedOrder.getId(), equalTo("111"));
        assertThat(updatedOrder.getPrice(), equalTo(500));
    }

    @Test
    void test_updateOrderWhenEmpty_throwsException() {
        assertThrows(NoSuchOrderException.class,
                () -> orderLogic.updateOrder("999", new NameValueList()));
    }

    @Test
    void test_deleteOrder_deletesOrder() {
        orderStore.addOrder(new Order("111", 100));


        orderLogic.deleteOrder("111");


        assertThat(orderLogic.getAll().isEmpty(), equalTo(true));
    }

    @Test
    void  test_deleteOrderWhenEmpty_throwsException() {
        assertThrows(NoSuchOrderException.class, () -> orderLogic.deleteOrder("999"));
    }
}
