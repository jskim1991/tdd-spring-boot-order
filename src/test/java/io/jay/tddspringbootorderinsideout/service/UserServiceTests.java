package io.jay.tddspringbootorderinsideout.service;

import io.jay.tddspringbootorderinsideout.domain.User;
import io.jay.tddspringbootorderinsideout.share.NameValue;
import io.jay.tddspringbootorderinsideout.share.NameValueList;
import io.jay.tddspringbootorderinsideout.store.UserJpaStore;
import io.jay.tddspringbootorderinsideout.store.UserStore;
import io.jay.tddspringbootorderinsideout.service.UserLogic;
import io.jay.tddspringbootorderinsideout.store.exception.NoSuchUserException;
import io.jay.tddspringbootorderinsideout.doubles.FakeUserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTests {

    private User john;
    private User sam;
    private UserStore userStore;
    private UserLogic userLogic;

    @BeforeEach
    void setUp() {
        john = User.builder().id("111").name("John").build();
        sam = User.builder().id("222").name("Sam").build();

        userStore = new UserJpaStore(new FakeUserJpaRepository());
        userLogic = new UserLogic(userStore);
    }

    @Test
    void test_getAllUsers_returnsUserList() {
        userStore.addUser(john);
        userStore.addUser(sam);


        List<User> users = userLogic.getAll();


        assertThat(users.get(0).getId(), equalTo("111"));
        assertThat(users.get(0).getName(), equalTo("John"));
        assertThat(users.get(1).getId(), equalTo("222"));
        assertThat(users.get(1).getName(), equalTo("Sam"));
    }

    @Test
    void test_getUser_returnsUser() {
        userStore.addUser(john);


        User user = userLogic.get("111");


        assertThat(user.getId(), equalTo("111"));
        assertThat(user.getName(), equalTo("John"));
    }

    @Test
    void test_getUserWhenEmpty_throwsException() {
        assertThrows(NoSuchUserException.class, () -> userLogic.get("999"));
    }

    @Test
    void test_addUser_returnsUser() {
        User saved = userLogic.add(john);


        assertThat(saved.getId(), equalTo("111"));
        assertThat(saved.getName(), equalTo("John"));
    }

    @Test
    void test_updateUser_returnsUpdatedUser() {
        userStore.addUser(john);
        NameValueList nameValueList = new NameValueList();
        nameValueList.add(new NameValue("phone", "010-1234-5678"));


        User updatedUser = userLogic.update("111", nameValueList);


        assertThat(updatedUser.getPhone(), equalTo("010-1234-5678"));
    }

    @Test
    void test_updateUserWhenEmpty_throwsException() {
        assertThrows(NoSuchUserException.class, () -> userLogic.update("999", new NameValueList()));
    }

    @Test
    void test_deleteUser_deletesUser() {
        userStore.addUser(john);


        userLogic.delete("111");


        assertThat(userLogic.getAll().isEmpty(), equalTo(true));
    }

    @Test
    void  test_deleteUserWhenEmpty_throwsException() {
        assertThrows(NoSuchUserException.class, () -> userLogic.delete("999"));
    }
}
