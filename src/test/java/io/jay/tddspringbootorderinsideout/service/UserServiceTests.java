package io.jay.tddspringbootorderinsideout.service;

import io.jay.tddspringbootorderinsideout.domain.User;
import io.jay.tddspringbootorderinsideout.share.NameValue;
import io.jay.tddspringbootorderinsideout.share.NameValueList;
import io.jay.tddspringbootorderinsideout.store.UserJpaStore;
import io.jay.tddspringbootorderinsideout.store.UserStore;
import io.jay.tddspringbootorderinsideout.store.exception.NoSuchUserException;
import io.jay.tddspringbootorderinsideout.store.FakeUserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTests {

    private User john;
    private User sam;
    private UserStore userStore;
    private UserLogic userLogic;

    @BeforeEach
    void setUp() {
        john = User.builder().name("John").build();
        sam = User.builder().name("Sam").build();

        userStore = new UserJpaStore(new FakeUserJpaRepository());
        userLogic = new UserLogic(userStore);
    }

    @Test
    void test_getAllUsers_returnsUserList() {
        userStore.addUser(john);
        userStore.addUser(sam);


        List<User> users = userLogic.getAll();


        assertThat(users.size(), equalTo(2));
        List<String> usernames = users.stream().map(User::getName).collect(Collectors.toList());
        assertThat(usernames.contains("John"), equalTo(true));
        assertThat(usernames.contains("Sam"), equalTo(true));
    }

    @Test
    void test_getUser_returnsUser() {
        User savedUser = userStore.addUser(john);


        User user = userLogic.get(savedUser.getId());


        assertThat(user.getId(), notNullValue());
        assertThat(user.getName(), equalTo("John"));
    }

    @Test
    void test_getUserWhenEmpty_throwsException() {
        assertThrows(NoSuchUserException.class, () -> userLogic.get("999"));
    }

    @Test
    void test_addUser_returnsUser() {
        User saved = userLogic.add(User.builder().name("John").build());


        assertThat(saved.getId(), notNullValue());
        assertThat(saved.getName(), equalTo("John"));
    }

    @Test
    void test_updateUser_returnsUpdatedUser() {
        User savedUser = userStore.addUser(john);
        String id = savedUser.getId();
        NameValueList nameValueList = new NameValueList();
        nameValueList.add(new NameValue("phone", "010-1234-5678"));


        User updatedUser = userLogic.update(id, nameValueList);


        assertThat(updatedUser.getPhone(), equalTo("010-1234-5678"));
    }

    @Test
    void test_updateUserWhenEmpty_throwsException() {
        assertThrows(NoSuchUserException.class, () -> userLogic.update("999", new NameValueList()));
    }

    @Test
    void test_deleteUser_deletesUser() {
        User savedUser = userStore.addUser(john);
        String id = savedUser.getId();


        userLogic.delete(id);


        assertThat(userLogic.getAll().isEmpty(), equalTo(true));
    }

    @Test
    void test_deleteUserWhenEmpty_throwsException() {
        assertThrows(NoSuchUserException.class, () -> userLogic.delete("999"));
    }

    @Test
    void test_loadUserByUsername_returnsUserWithCorrectEmail() {
        userStore.addUser(User.builder().email("email").build());


        User user = userLogic.loadUserByUsername("email");


        assertThat(user.getEmail(), equalTo("email"));
    }

    @Test
    void test_loadByUsernameWhenEmpty_throwsException() {
        assertThrows(UsernameNotFoundException.class, () -> userLogic.loadUserByUsername("email"));
    }
}
