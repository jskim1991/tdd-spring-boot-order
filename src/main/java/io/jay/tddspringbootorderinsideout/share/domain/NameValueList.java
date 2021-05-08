package io.jay.tddspringbootorderinsideout.share.domain;

import java.util.ArrayList;
import java.util.List;

public class NameValueList {

    private List<NameValue> nameValues;

    public NameValueList() {
        nameValues = new ArrayList<>();
    }

    public void add(NameValue nameValue) {
        this.nameValues.add(nameValue);
    }

    public List<NameValue> getNameValues() {
        return nameValues;
    }
}
