package io.jay.tddspringbootorderinsideout.store;

import io.jay.tddspringbootorderinsideout.user.User;

import java.util.List;

public interface UserStore {
    List<User> getAllUsers();

    User getUser(String id);

    User addUser(User user);

    User updateUser(User user);

    void deleteUser(String id);
}
