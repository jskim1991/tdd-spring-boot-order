package io.jay.tddspringbootorderinsideout.rest;

import io.jay.tddspringbootorderinsideout.domain.User;
import io.jay.tddspringbootorderinsideout.store.UserStore;

import java.util.List;

public class SpyStubUserStore implements UserStore {
    private String login_argument_email;
    private User getUserByEmail_return_value;

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
        return getUserByEmail_return_value;
    }

    public String getLogin_argument_email() {
        return login_argument_email;
    }

    public void setGetUserByEmail_return_value(User getUserByEmail_return_value) {
        this.getUserByEmail_return_value = getUserByEmail_return_value;
    }
}
