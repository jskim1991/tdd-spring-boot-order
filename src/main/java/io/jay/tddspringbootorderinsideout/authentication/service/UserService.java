package io.jay.tddspringbootorderinsideout.authentication.service;

import io.jay.tddspringbootorderinsideout.share.domain.NameValueList;
import io.jay.tddspringbootorderinsideout.authentication.domain.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User get(String id);

    User add(User user);

    User update(String id, NameValueList nameValueList);

    void delete(String id);
}
