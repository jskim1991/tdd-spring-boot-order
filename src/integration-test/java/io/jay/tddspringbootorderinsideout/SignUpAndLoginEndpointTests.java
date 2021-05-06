package io.jay.tddspringbootorderinsideout;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jay.tddspringbootorderinsideout.domain.User;
import io.jay.tddspringbootorderinsideout.domain.UserRole;
import io.jay.tddspringbootorderinsideout.rest.dto.LoginRequestDto;
import io.jay.tddspringbootorderinsideout.rest.dto.SignupRequestDto;
import io.jay.tddspringbootorderinsideout.store.UserStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SignUpAndLoginEndpointTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserStore userStore;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    @Transactional
    void test_login_returnsTokens() throws Exception {
        userStore.addUser(User.builder()
                .email("user@email.com")
                .password(passwordEncoder.encode("password"))
                .build());

        mockMvc.perform(post("/login")
                .content(mapper.writeValueAsString(
                        LoginRequestDto.builder()
                                .email("user@email.com")
                                .password("password")
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accessToken", notNullValue()))
                .andExpect(jsonPath("$.refreshToken", notNullValue()))
        ;
    }

    @Test
    @Transactional
    void test_signup_addsUser() throws Exception {
        mockMvc.perform(post("/signup")
                .content(mapper.writeValueAsString(
                        SignupRequestDto.builder()
                                .email("user@email.com")
                                .password("password")
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
        ;


        User user = userStore.getUserByEmail("user@email.com");
        assertThat(user.getId(), notNullValue());
        assertThat(passwordEncoder.matches("password", user.getPassword()), equalTo(true));
        assertThat(user.getRoles().get(0), equalTo(UserRole.ROLE_USER));
    }

    @Test
    @Transactional
    void test_loginWithWrongPassword_throwsException() throws Exception {
        mockMvc.perform(post("/signup")
                .content(mapper.writeValueAsString(
                        SignupRequestDto.builder()
                                .email("user@email.com")
                                .password("password")
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        ;


        NestedServletException exception = assertThrows(NestedServletException.class, () ->
                mockMvc.perform(post("/login")
                        .content(mapper.writeValueAsString(
                                LoginRequestDto.builder()
                                        .email("user@email.com")
                                        .password("wrong-password")
                                        .build()
                        ))
                        .contentType(MediaType.APPLICATION_JSON))
        );
        assertThat(exception.getCause().getMessage(), equalTo("Invalid username or password"));
    }

    @Test
    @Transactional
    void test_loginWithWrongEmail_throwsException() throws Exception {
        mockMvc.perform(post("/signup")
                .content(mapper.writeValueAsString(
                        SignupRequestDto.builder()
                                .email("user@email.com")
                                .password("password")
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        ;


        NestedServletException exception = assertThrows(NestedServletException.class, () ->
                mockMvc.perform(post("/login")
                        .content(mapper.writeValueAsString(
                                LoginRequestDto.builder()
                                        .email("wrong-user@email.com")
                                        .password("password")
                                        .build()
                        ))
                        .contentType(MediaType.APPLICATION_JSON))
        );
        assertThat(exception.getCause().getMessage(), equalTo("Invalid username or password"));
    }

    @Test
    void test_signupWithSameEmail_throwsException() throws Exception {
        LoginRequestDto loginRequest = LoginRequestDto.builder()
                .email("user@email.com")
                .password("password")
                .build();
        Exception exception = null;
        mockMvc.perform(post("/signup")
                .content(mapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON));


        try {
            mockMvc.perform(post("/signup")
                    .content(mapper.writeValueAsString(loginRequest))
                    .contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            exception = e;
        }


        assertThat(exception.getCause(), instanceOf(DataIntegrityViolationException.class));
    }
}
