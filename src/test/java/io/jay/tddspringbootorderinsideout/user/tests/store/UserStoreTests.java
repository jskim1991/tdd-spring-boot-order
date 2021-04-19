package io.jay.tddspringbootorderinsideout.user.tests.store;

import io.jay.tddspringbootorderinsideout.store.UserStore;
import io.jay.tddspringbootorderinsideout.user.User;
import io.jay.tddspringbootorderinsideout.user.UserJpaStore;
import io.jay.tddspringbootorderinsideout.user.store.exception.NoSuchUserException;
import io.jay.tddspringbootorderinsideout.user.store.repository.UserEntity;
import io.jay.tddspringbootorderinsideout.user.tests.doubles.FakeUserJpaRepository;
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
        john = new User();
        john.setId("111");
        john.setName("John");
        johnEntity = new UserEntity();
        johnEntity.setId(john.getId());
        johnEntity.setName(john.getName());


        sam = new User();
        sam.setId("222");
        sam.setName("Sam");
        samEntity = new UserEntity();
        samEntity.setId(sam.getId());
        samEntity.setName(sam.getName());

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
        john.setPhone("010-1234-5678");


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
