package io.jay.tddspringbootorderinsideout.store;

import io.jay.tddspringbootorderinsideout.domain.Order;
import io.jay.tddspringbootorderinsideout.doubles.FakeOrderJpaRepository;
import io.jay.tddspringbootorderinsideout.store.exception.NoSuchOrderException;
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
        OrderEntity savedEntity = fakeOrderJpaRepository.save(new OrderEntity(Order.builder().price(100).build()));


        List<Order> orders = orderStore.getAllOrders();


        assertThat(orders.get(0).getId(), equalTo(savedEntity.getId()));
        assertThat(orders.get(0).getPrice(), equalTo(100));
    }

    @Test
    void test_getAllOrdersWhenEmpty_returnsEmptyList() {
        assertThat(orderStore.getAllOrders().isEmpty(), equalTo(true));
    }

    @Test
    void test_getOrder_returnsOrder() {
        OrderEntity savedEntity = fakeOrderJpaRepository.save(new OrderEntity(Order.builder().price(100).build()));


        Order order = orderStore.getOrder(savedEntity.getId());


        assertThat(order.getId(), equalTo(savedEntity.getId()));
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
        Order order = orderStore.addOrder(Order.builder().price(100).build());


        assertThat(order.getId(), equalTo(order.getId()));
        assertThat(order.getPrice(), equalTo(100));
    }

    @Test
    void test_addOrder_persistsToDB() {
        Order savedOrder = orderStore.addOrder(Order.builder().price(100).build());


        Order order = orderStore.getAllOrders().get(0);
        assertThat(order.getId(), equalTo(savedOrder.getId()));
        assertThat(order.getPrice(), equalTo(100));
    }

    @Test
    void test_updateOrder_returnsOrder() {
        OrderEntity savedEntity = fakeOrderJpaRepository.save(new OrderEntity(Order.builder().price(100).build()));
        Order updateOrder = savedEntity.toDomain();
        updateOrder.setPrice(110);


        Order order = orderStore.updateOrder(updateOrder);


        assertThat(order.getId(), equalTo(updateOrder.getId()));
        assertThat(order.getPrice(), equalTo(110));
    }

    @Test
    void test_deleteOrder_deletesOrderFromDB() {
        OrderEntity savedEntity = fakeOrderJpaRepository.save(new OrderEntity(Order.builder().price(100).build()));


        orderStore.deleteOrder(savedEntity.getId());


        assertThat(orderStore.getAllOrders().isEmpty(), equalTo(true));
    }
}