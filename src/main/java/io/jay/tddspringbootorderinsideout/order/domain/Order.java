package io.jay.tddspringbootorderinsideout.order.domain;

import io.jay.tddspringbootorderinsideout.share.NameValue;
import io.jay.tddspringbootorderinsideout.share.NameValueList;

public class Order {

    private String id;
    private Integer price;

    public Order(String id, int price) {
        this.id = id;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setValues(NameValueList nameValueList) {
        for (NameValue nameValue : nameValueList.getNameValues()) {
            String name = nameValue.getName();
            switch (name) {
                case "price":
                    this.price = Integer.valueOf(nameValue.getValue());
                    break;
                default:
                    throw new IllegalArgumentException("No such field " + name);
            }
        }
    }
}
