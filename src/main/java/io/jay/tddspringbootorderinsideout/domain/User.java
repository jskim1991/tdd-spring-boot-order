package io.jay.tddspringbootorderinsideout.domain;

import io.jay.tddspringbootorderinsideout.share.NameValue;
import io.jay.tddspringbootorderinsideout.share.NameValueList;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class User {

    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String name;
    private String email;
    private String phone;
    private String password;
    @Builder.Default
    private List<Order> orders = new ArrayList<>();
    @Builder.Default
    private List<UserRole> roles = new ArrayList<>();

    public User() {
        this.id = UUID.randomUUID().toString();
        this.orders = new ArrayList<>();
        this.roles = new ArrayList<>();
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
