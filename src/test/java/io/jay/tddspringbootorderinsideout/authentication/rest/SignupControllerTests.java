package io.jay.tddspringbootorderinsideout.authentication.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jay.tddspringbootorderinsideout.authentication.domain.User;
import io.jay.tddspringbootorderinsideout.authentication.rest.dto.SignupRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SignupControllerTests {

    private MockMvc mockMvc;
    private SpyStubUserStore spyStubUserStore;
    private PasswordEncoder passwordEncoder;
    private ObjectMapper mapper = new ObjectMapper();
    private User user;
    private SignupRequestDto signupRequest;

    @BeforeEach
    void setup() {
        spyStubUserStore = new SpyStubUserStore();
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        mockMvc = MockMvcBuilders
                .standaloneSetup(new SignupController(spyStubUserStore, passwordEncoder))
                .build();
        user = User.builder().build();
        signupRequest = SignupRequestDto.builder()
                .email("user@email.com")
                .password("password")
                .build();

        spyStubUserStore.setAddUser_return_value(user);
    }

    @Test
    void test_signup_returnsOk() throws Exception {
        mockMvc.perform(post("/signup")
                .content(mapper.writeValueAsString(signupRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void test_signup_returnsId() throws Exception {
        String returnedId = mockMvc.perform(post("/signup")
                .content(mapper.writeValueAsString(signupRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(returnedId, equalTo(user.getId()));
    }

    @Test
    void test_signup_usesGivenEmailAndEncodedPassword() throws Exception {
        mockMvc.perform(post("/signup")
                .content(mapper.writeValueAsString(signupRequest))
                .contentType(MediaType.APPLICATION_JSON));


        assertThat(spyStubUserStore.getSignup_argument_email(), equalTo("user@email.com"));
        assertThat(passwordEncoder.matches("password", spyStubUserStore.getSignup_argument_password()), equalTo(true));
    }
}
