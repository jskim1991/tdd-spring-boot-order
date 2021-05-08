package io.jay.tddspringbootorderinsideout.shipping.store;

import io.jay.tddspringbootorderinsideout.shipping.domain.ShippingAddress;

import java.util.List;

public interface ShippingStore {
    List<ShippingAddress> getAll();

    ShippingAddress get(String id);

    ShippingAddress add(ShippingAddress shippingAddress);

    ShippingAddress update(ShippingAddress shippingAddress);

    void delete(String id);
}
