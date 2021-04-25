package io.jay.tddspringbootorderinsideout.store;

import io.jay.tddspringbootorderinsideout.domain.ShippingAddress;

import java.util.List;
import java.util.stream.Collectors;

public class ShippingJpaStore implements ShippingStore {

    private final ShippingJpaRepository shippingJpaRepository;

    public ShippingJpaStore(ShippingJpaRepository shippingJpaRepository) {
        this.shippingJpaRepository = shippingJpaRepository;
    }

    @Override
    public List<ShippingAddress> getAll() {
        return shippingJpaRepository.findAll()
                .stream()
                .map(ShippingAddressEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public ShippingAddress get(String id) {
        return shippingJpaRepository.findById(id).get().toDomain();
    }

    @Override
    public ShippingAddress add(ShippingAddress shippingAddress) {
        return shippingJpaRepository.save(new ShippingAddressEntity(shippingAddress)).toDomain();
    }

    @Override
    public ShippingAddress update(ShippingAddress shippingAddress) {
        return shippingJpaRepository.save(new ShippingAddressEntity(shippingAddress)).toDomain();
    }

    @Override
    public void delete(String id) {
        shippingJpaRepository.deleteById(id);
    }
}
