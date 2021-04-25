package io.jay.tddspringbootorderinsideout.store;

import io.jay.tddspringbootorderinsideout.domain.ShippingAddress;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ShippingAddressEntity {

    @Id
    private String id;
    private String name;
    private String address;
    private String zipCode;

    public ShippingAddressEntity(ShippingAddress shippingAddress) {
        BeanUtils.copyProperties(shippingAddress, this);
    }

    public ShippingAddress toDomain() {
        ShippingAddress shippingAddress = new ShippingAddress();
        BeanUtils.copyProperties(this, shippingAddress);
        return shippingAddress;
    }
}
