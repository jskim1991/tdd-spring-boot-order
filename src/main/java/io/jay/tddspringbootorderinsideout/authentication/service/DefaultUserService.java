package io.jay.tddspringbootorderinsideout.authentication.service;

import io.jay.tddspringbootorderinsideout.authentication.domain.User;
import io.jay.tddspringbootorderinsideout.share.domain.NameValueList;
import io.jay.tddspringbootorderinsideout.authentication.store.UserStore;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultUserService implements UserService, UserDetailsService {

    private final UserStore userStore;

    public DefaultUserService(UserStore userStore) {
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
        // TODO: refactor
        userStore.getUser(id);
        userStore.deleteUser(id);
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = userStore.getUserByEmail(email);
            return user;
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
