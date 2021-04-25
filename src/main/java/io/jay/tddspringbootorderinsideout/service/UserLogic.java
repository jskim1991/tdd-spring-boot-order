package io.jay.tddspringbootorderinsideout.service;

import io.jay.tddspringbootorderinsideout.share.NameValueList;
import io.jay.tddspringbootorderinsideout.store.UserStore;
import io.jay.tddspringbootorderinsideout.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLogic implements UserService {

    private UserStore userStore;

    public UserLogic(UserStore userStore) {
        this.userStore = userStore;
    }

    @Override
    public List<User> getAll() {
        return userStore.getAllUsers();
    }

    @Override
    public User get(String id) {
        return userStore.getUser(id);
    }

    @Override
    public User add(User user) {
        return userStore.addUser(user);
    }

    @Override
    public User update(String id, NameValueList nameValueList) {
        User user = userStore.getUser(id);
        user.setValues(nameValueList);
        return userStore.updateUser(user);
    }

    @Override
    public void delete(String id) {
        userStore.getUser(id);
        userStore.deleteUser(id);
    }
}
