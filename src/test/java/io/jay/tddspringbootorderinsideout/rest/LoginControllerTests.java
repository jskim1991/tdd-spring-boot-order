package io.jay.tddspringbootorderinsideout.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jay.tddspringbootorderinsideout.domain.User;
import io.jay.tddspringbootorderinsideout.rest.dto.LoginRequestDto;
import io.jay.tddspringbootorderinsideout.util.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginControllerTests {

    private ObjectMapper mapper;
    private MockMvc mockMvc;
    private SpyStubUserStore spyStubUserStore;
    private SpyStubJwtTokenUtil spyStubjwtTokenUtil;

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper();
        spyStubUserStore = new SpyStubUserStore();
        spyStubjwtTokenUtil = new SpyStubJwtTokenUtil();
        mockMvc = MockMvcBuilders
                .standaloneSetup(new LoginController(spyStubUserStore, spyStubjwtTokenUtil))
                .build();

        spyStubjwtTokenUtil.setCreateAccessToken_return_value("some access token");
        spyStubjwtTokenUtil.setCreateRefreshToken_return_value("some refresh token");
    }

    @Test
    void test_login_returnsOk() throws Exception {
        mockMvc.perform(post("/login")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void test_login_returnsTokenResponse() throws Exception {
        mockMvc.perform(post("/login")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accessToken", notNullValue()))
                .andExpect(jsonPath("$.refreshToken", notNullValue()))
        ;
    }

    @Test
    void test_login_usesGivenEmail() throws Exception {
        mockMvc.perform(post("/login")
                .content(mapper.writeValueAsString(
                        LoginRequestDto.builder()
                                .email("user@email.com")
                                .password("password").build()
                ))
                .contentType(MediaType.APPLICATION_JSON));
        assertThat(spyStubUserStore.getLogin_argument_email(), equalTo("user@email.com"));
    }

    @Test
    void test_login_usesRetrievedUser() throws Exception {
        User user = User.builder()
                .email("user@email.com")
                .build();
        spyStubUserStore.setGetUserByEmail_return_value(user);


        mockMvc.perform(post("/login")
                .content(mapper.writeValueAsString(
                        LoginRequestDto.builder()
                                .email("user@email.com")
                                .password("password").build()
                ))
                .contentType(MediaType.APPLICATION_JSON));


        assertThat(spyStubjwtTokenUtil.getCreateAccessToken_argument_user(),equalTo(user));
        assertThat(spyStubjwtTokenUtil.getCreateRefreshToken_argument_user(),equalTo(user));
    }
}
