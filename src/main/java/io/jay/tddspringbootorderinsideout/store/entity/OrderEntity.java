package io.jay.tddspringbootorderinsideout.store.entity;

import io.jay.tddspringbootorderinsideout.domain.Order;
import io.jay.tddspringbootorderinsideout.domain.User;
import lombok.Getter;
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

    public OrderEntity(Order order) {
        BeanUtils.copyProperties(order, this);
        if (order.getUser() != null) {
            this.userEntity = new UserEntity(order.getUser());
        }
    }

    public Order toDomain() {
        Order order = new Order();
        BeanUtils.copyProperties(this, order);
        if (this.userEntity != null) {
            User user = new User();
            BeanUtils.copyProperties(this.userEntity, user);
            order.setUser(user);
        }
        return order;
    }
}