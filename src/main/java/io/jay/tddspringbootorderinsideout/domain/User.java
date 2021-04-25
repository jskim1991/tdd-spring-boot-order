package io.jay.tddspringbootorderinsideout.domain;

import io.jay.tddspringbootorderinsideout.share.NameValue;
import io.jay.tddspringbootorderinsideout.share.NameValueList;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class User {

    private String id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private List<Order> orders;

    public User() {
        this.id = UUID.randomUUID().toString();
        this.orders = new ArrayList<>();
    }

    @Builder
    public User(String name, String email, String phone, String password, List<Order> orders) {
        this();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.orders = orders;
    }

    public void setValues(NameValueList nameValueList) {
        for (NameValue nameValue : nameValueList.getNameValues()) {
            String name = nameValue.getName();
            String value = nameValue.getValue();
            switch (name) {
                case "name":
                    this.name = value;
                    break;
                case "email":
                    this.email = value;
                    break;
                case "phone":
                    this.phone = value;
                    break;
                case "password":
                    this.password = value;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field " + name);
            }
        }
    }
}
