package io.jay.tddspringbootorderinsideout.rest;

import io.jay.tddspringbootorderinsideout.domain.User;
import io.jay.tddspringbootorderinsideout.store.UserStore;

import java.util.List;

public class SpyStubUserStore implements UserStore {
    private String login_argument_email;

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User getUser(String id) {
        return null;
    }

    @Override
    public User addUser(User user) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public void deleteUser(String id) {

    }

    @Override
    public User getUserByEmail(String email) {
        login_argument_email = email;
        return null;
    }

    public String getLogin_argument_email() {
        return login_argument_email;
    }
}
