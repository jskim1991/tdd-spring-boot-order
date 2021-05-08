package io.jay.tddspringbootorderinsideout.shipping.domain;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
public class ShippingAddress {
    private String id;
    private String name;
    private String address;
    private String zipCode;

    public ShippingAddress() {
        this.id = UUID.randomUUID().toString();
    }

    @Builder
    public ShippingAddress(String name, String address, String zipCode) {
        this();
        this.name = name;
        this.address = address;
        this.zipCode = zipCode;
    }
}
