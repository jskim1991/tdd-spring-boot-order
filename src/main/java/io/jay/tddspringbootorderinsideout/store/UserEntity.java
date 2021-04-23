package io.jay.tddspringbootorderinsideout.store;

import io.jay.tddspringbootorderinsideout.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Builder
    public UserEntity(String name, String email, String phone, String password) {
        this();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public User toDomain() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }
}
