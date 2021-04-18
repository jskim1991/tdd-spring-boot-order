package io.jay.tddspringbootorderinsideout.tests;

import io.jay.tddspringbootorderinsideout.domain.Order;
import io.jay.tddspringbootorderinsideout.share.NameValue;
import io.jay.tddspringbootorderinsideout.share.NameValueList;
import io.jay.tddspringbootorderinsideout.store.OrderJpaStore;
import io.jay.tddspringbootorderinsideout.store.OrderStore;
import io.jay.tddspringbootorderinsideout.store.exception.NoSuchOrderException;
import io.jay.tddspringbootorderinsideout.store.repository.OrderEntity;
import io.jay.tddspringbootorderinsideout.tests.doubles.repository.FakeOrderJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderStoreTests {

    private FakeOrderJpaRepository fakeOrderJpaRepository;
    private OrderStore orderStore;

    @BeforeEach
    void setUp() {
        fakeOrderJpaRepository = new FakeOrderJpaRepository();
        orderStore = new OrderJpaStore(fakeOrderJpaRepository);
    }

    @Test
    void test_getAllOrders_returnsListOfOrders() {
        fakeOrderJpaRepository.save(new OrderEntity("123", 100));


        List<Order> orders = orderStore.getAllOrders();


        assertThat(orders.get(0).getId(), equalTo("123"));
        assertThat(orders.get(0).getPrice(), equalTo(100));
    }

    @Test
    void test_getAllOrdersWhenEmpty_returnsEmptyList() {
        assertThat(orderStore.getAllOrders().isEmpty(), equalTo(true));
    }

    @Test
    void test_getOrder_returnsOrder() {
        fakeOrderJpaRepository.save(new OrderEntity("123", 100));


        Order order = orderStore.getOrder("123");


        assertThat(order.getId(), equalTo("123"));
        assertThat(order.getPrice(), equalTo(100));
    }

    @Test
    void test_getOrderWhenEmpty_throwsException() {
        NoSuchOrderException thrown = assertThrows(NoSuchOrderException.class,
                () -> orderStore.getOrder("999"));
        assertThat(thrown.getMessage(), equalTo("No such order for id 999"));
    }

    @Test
    void test_addOrder_returnsOrder() {
        Order order = orderStore.addOrder(new Order("123", 100));


        assertThat(order.getId(), equalTo("123"));
        assertThat(order.getPrice(), equalTo(100));
    }

    @Test
    void test_addOrder_persistsToDB() {
        orderStore.addOrder(new Order("123", 100));


        Order order = orderStore.getAllOrders().get(0);
        assertThat(order.getId(), equalTo("123"));
        assertThat(order.getPrice(), equalTo(100));
    }

    @Test
    void test_updateOrder_returnsOrder() {
        fakeOrderJpaRepository.save(new OrderEntity("123", 100));
        NameValueList nameValueList = new NameValueList();
        nameValueList.add(new NameValue("price", "110"));

        Order order = orderStore.updateOrder("123", nameValueList);


        assertThat(order.getId(), equalTo("123"));
        assertThat(order.getPrice(), equalTo(110));
    }

    @Test
    void test_updateOrderWhenEmpty_throwsException() {
        NoSuchOrderException thrown = assertThrows(NoSuchOrderException.class,
                () -> orderStore.updateOrder("999", new NameValueList()));
        assertThat(thrown.getMessage(), equalTo("No such order for id 999"));
    }

    @Test
    void test_deleteOrder_deletesOrderFromDB() {
        fakeOrderJpaRepository.save(new OrderEntity("123", 100));


        orderStore.deleteOrder("123");


        assertThat(orderStore.getAllOrders().isEmpty(), equalTo(true));
    }

    @Test
    void test_deleteOrderWhenEmpty_throwsException() {
        NoSuchOrderException thrown = assertThrows(NoSuchOrderException.class,
                () -> orderStore.deleteOrder("999"));
        assertThat(thrown.getMessage(), equalTo("No such order for id 999"));
    }
}