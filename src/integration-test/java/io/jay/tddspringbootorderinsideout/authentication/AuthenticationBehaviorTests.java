package io.jay.tddspringbootorderinsideout.authentication;

import io.jay.tddspringbootorderinsideout.authentication.domain.User;
import io.jay.tddspringbootorderinsideout.authentication.domain.UserRole;
import io.jay.tddspringbootorderinsideout.authentication.store.UserStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
public class AuthenticationBehaviorTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserStore userStore;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private AuthenticationTestHelper testHelper;

    @BeforeEach
    void setUp() {
        testHelper = new AuthenticationTestHelper(mockMvc);
    }

    @Test
    @Transactional
    void test_signup_addsUserToStore() throws Exception {
        testHelper.signUp("user@email.com", "password");


        User user = userStore.getUserByEmail("user@email.com");
        assertThat(user.getId(), notNullValue());
        assertThat(passwordEncoder.matches("password", user.getPassword()), equalTo(true));
        assertThat(user.getRoles().get(0), equalTo(UserRole.ROLE_USER));
    }

    @Test
    @Transactional
    void test_login_returnsTokens() throws Exception {
        testHelper.signUp("user@email.com", "password");


        testHelper.login("user@email.com", "password")
                .andExpect(jsonPath("$.accessToken", notNullValue()))
                .andExpect(jsonPath("$.refreshToken", notNullValue()))
        ;
    }

    @Test
    @Transactional
    void test_loginWithWrongPassword_throwsException() throws Exception {
        testHelper.signUp("user@email.com", "password");


        NestedServletException exception = assertThrows(NestedServletException.class, () ->
                testHelper.login("user@email.com", "WRONG-password")
        );
        assertThat(exception.getCause().getMessage(), equalTo("Invalid username or password"));
    }

    @Test
    @Transactional
    void test_loginWithWrongEmail_throwsException() throws Exception {
        testHelper.signUp("user@email.com", "password");


        NestedServletException exception = assertThrows(NestedServletException.class, () ->
                testHelper.login("WRONG-user@email.com", "password")
        );
        assertThat(exception.getCause().getMessage(), equalTo("Invalid username or password"));
    }

    @Test
    void test_signupWithSameEmail_throwsException() throws Exception {
        testHelper.signUp("user@email.com", "password");


        Exception exception = null;
        try {
            testHelper.signUp("user@email.com", "different-password");
        } catch (Exception e) {
            exception = e;
        }


        assertThat(exception.getCause(), instanceOf(DataIntegrityViolationException.class));
    }
}
