package io.jay.tddspringbootorderinsideout.tests.service;

import io.jay.tddspringbootorderinsideout.domain.Order;
import io.jay.tddspringbootorderinsideout.service.OrderLogic;
import io.jay.tddspringbootorderinsideout.service.OrderService;
import io.jay.tddspringbootorderinsideout.store.OrderJpaStore;
import io.jay.tddspringbootorderinsideout.tests.doubles.repository.FakeOrderJpaRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class OrderServiceTests {

    @Test
    void test_getAllOrders_returnsList() {
        OrderService orderService = new OrderLogic();


        List<Order> orders = orderService.getAll();


        assertThat(orders.get(0).getId(), equalTo("123"));
        assertThat(orders.get(0).getPrice(), equalTo(100));
        assertThat(orders.get(1).getId(), equalTo("222"));
        assertThat(orders.get(1).getPrice(), equalTo(200));
    }
}
