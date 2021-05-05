package io.jay.tddspringbootorderinsideout.rest;

import io.jay.tddspringbootorderinsideout.domain.User;
import io.jay.tddspringbootorderinsideout.store.UserStore;

import java.util.List;

public class SpyStubUserStore implements UserStore {
    private String login_argument_email;
    private User getUserByEmail_return_value;
    private User addUser_return_value;
    private String signup_argument_email;
    private String signup_argument_password;

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
        signup_argument_email = user.getEmail();
        signup_argument_password = user.getPassword();
        return addUser_return_value;
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


    public void setAddUser_return_value(User addUser_return_value) {
        this.addUser_return_value = addUser_return_value;
    }

    public String getSignup_argument_email() {
        return signup_argument_email;
    }

    public String getSignup_argument_password() {
        return signup_argument_password;
    }
}
