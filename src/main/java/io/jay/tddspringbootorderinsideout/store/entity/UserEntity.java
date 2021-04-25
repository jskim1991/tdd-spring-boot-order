package io.jay.tddspringbootorderinsideout.store.entity;

import io.jay.tddspringbootorderinsideout.domain.Order;
import io.jay.tddspringbootorderinsideout.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "users")
@Table(name = "TB_USER")
@Getter
@Setter
public class UserEntity {

    @Id
    @Column(name = "user_id")
    private String id;
    private String name;
    private String email;
    private String phone;
    private String password;

    @OneToMany(mappedBy = "userEntity", orphanRemoval = true)
    private List<OrderEntity> orders = new ArrayList<>();

    public UserEntity(User user) {
        BeanUtils.copyProperties(user, this);
    }

    public UserEntity() {
        this.id = UUID.randomUUID().toString();
    }

    public User toDomain() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        if (this.orders.size() > 0) {
            for (OrderEntity orderEntity : this.orders) {
                Order order = new Order();
                BeanUtils.copyProperties(orderEntity, order);
                user.getOrders().add(order);
            }
        }
        return user;
    }
}
