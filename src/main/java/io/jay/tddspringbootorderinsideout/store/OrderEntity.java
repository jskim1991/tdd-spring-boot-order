package io.jay.tddspringbootorderinsideout.store;

import io.jay.tddspringbootorderinsideout.domain.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity(name = "order")
@Table(name = "TB_ORDER")
@Getter
@Setter
public class OrderEntity {

    @Id
    private String id;
    private Timestamp creationTimestamp;
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private UserEntity userEntity;

    public OrderEntity() {
        this.id = UUID.randomUUID().toString();
    }

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
        BeanUtils.copyProperties(order, this);
    }

    public Order toDomain() {
        return new Order(this.id, this.price);
    }
}
