package io.jay.tddspringbootorderinsideout.user.store.repository;

import io.jay.tddspringbootorderinsideout.user.User;
import org.springframework.beans.BeanUtils;

public class UserEntity {

    private String id;
    private String name;
    private String email;
    private String phone;
    private String password;

    public UserEntity() {
    }

    public UserEntity(User user) {
        BeanUtils.copyProperties(user, this);
    }

    public User toDomain() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
