package io.jay.tddspringbootorderinsideout.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jay.tddspringbootorderinsideout.authentication.domain.User;
import io.jay.tddspringbootorderinsideout.authentication.rest.dto.LoginRequestDto;
import io.jay.tddspringbootorderinsideout.authentication.rest.dto.SignupRequestDto;
import io.jay.tddspringbootorderinsideout.authentication.store.UserStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
public class EndpointAccessTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserStore userStore;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    @Transactional
    void test_login_isAccessible() throws Exception {

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
                .andExpect(status().isOk())
        ;
    }

    @Test
    @Transactional
    void test_signup_isAccessible() throws Exception {
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
    }

    @Test
    void test_getOrders_returnsForbidden() throws Exception {
        mockMvc.perform(get("/orders"))
                .andExpect(status().isForbidden());
    }

    @Test
    void test_getUsers_returnsForbidden() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isForbidden());
    }
}
