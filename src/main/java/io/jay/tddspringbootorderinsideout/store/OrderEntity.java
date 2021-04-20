package io.jay.tddspringbootorderinsideout.store;

import io.jay.tddspringbootorderinsideout.domain.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "order")
@Table(name = "TB_ORDER")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {

    @Id
    private String id;
    private Timestamp creationTimestamp;
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private UserEntity userEntity;

    public OrderEntity(String id, Integer price) {
        this.id = id;
        this.price = price;
    }

    @Builder
    public OrderEntity(String id, Timestamp creationTimestamp, Integer price, UserEntity userEntity) {
        this.id = id;
        this.creationTimestamp = creationTimestamp;
        this.price = price;
        this.userEntity = userEntity;
    }

    public OrderEntity(Order order) {
        this.id = order.getId();
        this.price = order.getPrice();
    }

    public Order toDomain() {
        return new Order(this.id, this.price);
    }
}
