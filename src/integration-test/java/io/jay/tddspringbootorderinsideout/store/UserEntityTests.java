package io.jay.tddspringbootorderinsideout.store;

import io.jay.tddspringbootorderinsideout.store.jpa.OrderJpaRepository;
import io.jay.tddspringbootorderinsideout.store.jpa.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
public class UserEntityTests {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Autowired
    private TestEntityManager em;

    private String userId = null;

    @BeforeEach
    void setup() {
        UserEntity userEntity = UserEntity.builder()
                .name("Jay").build();

        UserEntity savedUserEntity = userJpaRepository.save(userEntity);
        userId = savedUserEntity.getId();

        List<OrderEntity> orderEntities = new ArrayList<>();
        orderEntities.add(OrderEntity.builder()
                .userEntity(userEntity)
                .price(100).build());
        orderEntities.add(OrderEntity.builder()
                .userEntity(userEntity)
                .price(200).build());

        orderJpaRepository.saveAll(orderEntities);

        em.flush();
        em.clear();
    }

    @Test
    void test_savedOrderAndUser_referencesEachOther() {
        assertThat(orderJpaRepository.findAll().get(0).getUserEntity().getId(), equalTo(userId));
        assertThat(userJpaRepository.findById(userId).get().getOrders().size(), equalTo(2));
    }

    @Test
    void test_deletingUser_deletesOrders() {
        userJpaRepository.deleteAll();


        assertThat(orderJpaRepository.findAll().size(), equalTo(0));
    }
}
