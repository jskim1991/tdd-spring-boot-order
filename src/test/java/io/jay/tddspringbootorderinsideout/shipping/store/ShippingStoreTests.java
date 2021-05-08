package io.jay.tddspringbootorderinsideout.shipping.store;

import io.jay.tddspringbootorderinsideout.shipping.domain.ShippingAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@DataJpaTest
public class ShippingStoreTests {

    @Autowired
    private ShippingJpaRepository shippingJpaRepository;

    private ShippingStore shippingStore;
    private ShippingAddress homeAddress;

    @BeforeEach
    void setUp() {
        shippingStore = new ShippingJpaStore(shippingJpaRepository);
        homeAddress = ShippingAddress.builder()
                .name("home")
                .address("17 young street")
                .zipCode("12345")
                .build();
    }

    @Test
    void test_getAll_returnsList() {
        shippingJpaRepository.saveAll(
                Arrays.asList(new ShippingAddressEntity[] {
                        new ShippingAddressEntity(new ShippingAddress()),
                        new ShippingAddressEntity(new ShippingAddress())
                }));


        List<ShippingAddress> addresses = shippingStore.getAll();


        assertThat(addresses.size(), equalTo(2));
    }

    @Test
    void test_getShippingAddress_returnsShippingAddress() {
        ShippingAddressEntity savedEntity = shippingJpaRepository.save(new ShippingAddressEntity(homeAddress));
        ShippingAddress actual = shippingStore.get(savedEntity.getId());


        assertThat(actual.getId(), notNullValue());
        assertThat(actual.getName(), equalTo("home"));
        assertThat(actual.getAddress(), equalTo("17 young street"));
        assertThat(actual.getZipCode(), equalTo("12345"));
    }

    @Test
    void test_addShippingAddress_returnsShippingAddress() {
        ShippingAddress saved = shippingStore.add(homeAddress);


        assertThat(saved.getId(), notNullValue());
        assertThat(saved.getName(), equalTo("home"));
        assertThat(saved.getAddress(), equalTo("17 young street"));
        assertThat(saved.getZipCode(), equalTo("12345"));
    }

    @Test
    void test_addShippingAddress_persistsToDB() {
        shippingStore.add(homeAddress);


        assertThat(shippingJpaRepository.findAll().size(), equalTo(1));
    }

    @Test
    void test_updateShippingAddress_returnsUpdatedShippingAddress() {
        ShippingAddressEntity savedEntity = shippingJpaRepository.save(new ShippingAddressEntity(homeAddress));
        savedEntity.setZipCode("99999");


        ShippingAddress updated = shippingStore.update(savedEntity.toDomain());


        assertThat(updated.getZipCode(), equalTo("99999"));
    }

    @Test
    void test_delete_deletesEntryInDB() {
        ShippingAddressEntity savedEntity = shippingJpaRepository.save(new ShippingAddressEntity(homeAddress));


        shippingStore.delete(savedEntity.getId());


        assertThat(shippingJpaRepository.findAll().size(), equalTo(0));
    }
}
