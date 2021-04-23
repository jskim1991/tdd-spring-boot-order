package io.jay.tddspringbootorderinsideout.domain;

import io.jay.tddspringbootorderinsideout.share.JsonUtil;
import io.jay.tddspringbootorderinsideout.share.NameValue;
import io.jay.tddspringbootorderinsideout.share.NameValueList;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    private String id;
    private Timestamp creationTimestamp;
    private Integer price;
    private User user;

    public Order(String id, int price) {
        this.id = id;
        this.price = price;
    }

    public void setValues(NameValueList nameValueList) {
        for (NameValue nameValue : nameValueList.getNameValues()) {
            String name = nameValue.getName();
            String value = nameValue.getValue();
            switch (name) {
                case "price":
                    this.price = Integer.valueOf(nameValue.getValue());
                    break;
                case "user":
                    this.user = JsonUtil.fromJson(value, User.class);
                    break;
                default:
                    throw new IllegalArgumentException("No such field " + name);
            }
        }
    }
}
