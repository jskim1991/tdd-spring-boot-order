package io.jay.tddspringbootorderinsideout.authentication.store;

import io.jay.tddspringbootorderinsideout.share.domain.NameValue;
import io.jay.tddspringbootorderinsideout.share.domain.NameValueList;
import io.jay.tddspringbootorderinsideout.authentication.exception.NoSuchUserException;
import io.jay.tddspringbootorderinsideout.authentication.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
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
        john = User.builder().name("John").email("john@email.com").build();
        johnEntity = new UserEntity(john);


        sam = User.builder().name("Sam").build();
        samEntity = new UserEntity(sam);

        fakeUserJpaRepository = new FakeUserJpaRepository();
        userStore = new UserJpaStore(fakeUserJpaRepository);
    }

    @Test
    void test_getAllUsers_returnsUserList() {
        fakeUserJpaRepository.save(johnEntity);
        fakeUserJpaRepository.save(samEntity);


        List<User> users = userStore.getAllUsers();


        assertThat(users.size(), equalTo(2));
        List<String> usernames = users.stream().map(User::getName).collect(Collectors.toList());
        assertThat(usernames.contains("John"), equalTo(true));
        assertThat(usernames.contains("Sam"), equalTo(true));
    }

    @Test
    void test_getUser_returnsUser() {
        UserEntity savedEntity = fakeUserJpaRepository.save(johnEntity);


        User user = userStore.getUser(savedEntity.getId());


        assertThat(user.getId(), notNullValue());
        assertThat(user.getName(), equalTo("John"));
    }

    @Test
    void test_getUserWhenEmpty_throwsException() {
        assertThrows(NoSuchUserException.class, () -> userStore.getUser("999"));
    }

    @Test
    void test_addUser_returnsUser() {
        User savedUser = userStore.addUser(john);


        assertThat(savedUser.getId(), notNullValue());
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
        UserEntity savedEntity = fakeUserJpaRepository.save(johnEntity);
        String id = savedEntity.getId();

        userStore.deleteUser(id);


        assertThat(userStore.getAllUsers().isEmpty(), equalTo(true));
    }

    @Test
    void test_getUserByEmail_returnsUserWithCorrectEmail() {
        fakeUserJpaRepository.save(johnEntity);


        User actualUser = userStore.getUserByEmail("john@email.com");


        assertThat(actualUser.getEmail(), equalTo("john@email.com"));
    }

    @Test
    void test_getUserWithWrongEmail_throwsException() {
        fakeUserJpaRepository.save(johnEntity);


        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userStore.getUserByEmail("some-other-email@email.com"));


        assertThat(exception.getMessage(), equalTo("Invalid username or password"));
    }
}
