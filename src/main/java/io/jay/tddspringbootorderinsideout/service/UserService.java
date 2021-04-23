package io.jay.tddspringbootorderinsideout.service;

import io.jay.tddspringbootorderinsideout.share.NameValueList;
import io.jay.tddspringbootorderinsideout.domain.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User get(String id);

    User add(User user);

    User update(String id, NameValueList nameValueList);

    void delete(String id);
}