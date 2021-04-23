package io.jay.tddspringbootorderinsideout.store;

import io.jay.tddspringbootorderinsideout.share.NameValue;
import io.jay.tddspringbootorderinsideout.share.NameValueList;
import io.jay.tddspringbootorderinsideout.store.exception.NoSuchUserException;
import io.jay.tddspringbootorderinsideout.domain.User;
import io.jay.tddspringbootorderinsideout.doubles.FakeUserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserStoreTests {

    private User john;
    private User sam;
    private UserEntity johnEntity;
    private UserEntity samEntity;

    private FakeUserJpaRepository fakeUserJpaRepository;
    private UserJpaStore userStore;

    @BeforeEach
    void setUp() {
        john = User.builder().id("111").name("John").build();
        johnEntity = new UserEntity.UserEntityBuilder().id(john.getId()).name(john.getName()).build();


        sam = User.builder().id("222").name("Sam").build();
        samEntity = new UserEntity.UserEntityBuilder().id(sam.getId()).name(sam.getName()).build();

        fakeUserJpaRepository = new FakeUserJpaRepository();
        userStore = new UserJpaStore(fakeUserJpaRepository);
    }

    @Test
    void test_getAllUsers_returnsUserList() {
        fakeUserJpaRepository.save(johnEntity);
        fakeUserJpaRepository.save(samEntity);


        List<User> users = userStore.getAllUsers();


        assertThat(users.get(0).getId(), equalTo("111"));
        assertThat(users.get(0).getName(), equalTo("John"));
        assertThat(users.get(1).getId(), equalTo("222"));
        assertThat(users.get(1).getName(), equalTo("Sam"));
    }

    @Test
    void test_getUser_returnsUser() {
        fakeUserJpaRepository.save(johnEntity);


        User user = userStore.getUser("111");


        assertThat(user.getId(), equalTo("111"));
        assertThat(user.getName(), equalTo("John"));
    }

    @Test
    void test_getUserWhenEmpty_throwsException() {
        assertThrows(NoSuchUserException.class, () -> userStore.getUser("999"));
    }

    @Test
    void test_addUser_returnsUser() {
        User savedUser = userStore.addUser(john);


        assertThat(savedUser.getId(), equalTo("111"));
        assertThat(savedUser.getName(), equalTo("John"));
    }

    @Test
    void test_updateUser_returnsUpdatedUser() {
        fakeUserJpaRepository.save(johnEntity);
        NameValueList nameValueList = new NameValueList();
        nameValueList.add(new NameValue("phone", "010-1234-5678"));
        john.setValues(nameValueList);


        User updatedUser = userStore.updateUser(john);


        assertThat(updatedUser.getPhone(), equalTo("010-1234-5678"));
    }

    @Test
    void test_deleteUser_deletesUser() {
        fakeUserJpaRepository.save(johnEntity);


        userStore.deleteUser("111");


        assertThat(userStore.getAllUsers().isEmpty(), equalTo(true));
    }
}
