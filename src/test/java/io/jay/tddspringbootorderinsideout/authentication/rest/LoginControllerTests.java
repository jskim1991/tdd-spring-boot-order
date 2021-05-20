package io.jay.tddspringbootorderinsideout.authentication.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jay.tddspringbootorderinsideout.authentication.domain.User;
import io.jay.tddspringbootorderinsideout.authentication.rest.dto.LoginRequestDto;
import io.jay.tddspringbootorderinsideout.share.token.SpyStubEndpointAccessTokenGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginControllerTests {

    private ObjectMapper mapper;
    private MockMvc mockMvc;
    private SpyStubUserStore spyStubUserStore;
    private SpyStubEndpointAccessTokenGenerator spyStubTokenGenerator;
    private PasswordEncoder passwordEncoder;
    private User user;
    private LoginRequestDto loginRequestDto;

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper();
        spyStubUserStore = new SpyStubUserStore();
        spyStubTokenGenerator = new SpyStubEndpointAccessTokenGenerator();
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        mockMvc = MockMvcBuilders
                .standaloneSetup(new LoginController(spyStubUserStore, spyStubTokenGenerator, passwordEncoder))
                .build();

        spyStubTokenGenerator.setCreateAccessToken_return_value("some access token");
        spyStubTokenGenerator.setCreateRefreshToken_return_value("some refresh token");

        loginRequestDto = LoginRequestDto.builder()
                .email("user@email.com")
                .password("password")
                .build();
        user = User.builder()
                .email("user@email.com")
                .password(passwordEncoder.encode("password"))
                .build();
        spyStubUserStore.setGetUserByEmail_return_value(user);
    }

    @Test
    void test_login_returnsOk() throws Exception {
        mockMvc.perform(post("/login")
                .content(mapper.writeValueAsString(loginRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void test_login_returnsTokenResponse() throws Exception {
        mockMvc.perform(post("/login")
                .content(mapper.writeValueAsString(loginRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accessToken", notNullValue()))
                .andExpect(jsonPath("$.refreshToken", notNullValue()))
        ;
    }

    @Test
    void test_login_usesGivenEmail() throws Exception {
        mockMvc.perform(post("/login")
                .content(mapper.writeValueAsString(loginRequestDto))
                .contentType(MediaType.APPLICATION_JSON));
        assertThat(spyStubUserStore.getLogin_argument_email(), equalTo("user@email.com"));
    }

    @Test
    void test_login_usesRetrievedUser() throws Exception {
        mockMvc.perform(post("/login")
                .content(mapper.writeValueAsString(loginRequestDto))
                .contentType(MediaType.APPLICATION_JSON));


        assertThat(spyStubTokenGenerator.getCreateAccessToken_argument_email(),equalTo(user.getEmail()));
        assertThat(spyStubTokenGenerator.getCreateAccessToken_argument_roles(),equalTo(user.getRoles()));

        assertThat(spyStubTokenGenerator.getCreateRefreshToken_argument_email(),equalTo(user.getEmail()));
        assertThat(spyStubTokenGenerator.getCreateRefreshToken_argument_roles(),equalTo(user.getRoles()));
    }

    @Test
    void test_loginUsingWrongPassword_throwsException() {
        LoginRequestDto requestWithWrongPassword = LoginRequestDto.builder()
                .email("user@email.com")
                .password("wrong-password")
                .build();


        NestedServletException exception = assertThrows(NestedServletException.class, () ->
        mockMvc.perform(post("/login")
                .content(mapper.writeValueAsString(requestWithWrongPassword))
                .contentType(MediaType.APPLICATION_JSON)));
        assertThat(exception.getCause().getMessage(), equalTo("Invalid username or password"));
    }
}
