package io.jay.tddspringbootorderinsideout.store;

import io.jay.tddspringbootorderinsideout.domain.ShippingAddress;

import java.util.List;

public interface ShippingStore {
    List<ShippingAddress> getAll();

    ShippingAddress get(String id);

    ShippingAddress add(ShippingAddress shippingAddress);

    ShippingAddress update(ShippingAddress shippingAddress);

    void delete(String id);
}
