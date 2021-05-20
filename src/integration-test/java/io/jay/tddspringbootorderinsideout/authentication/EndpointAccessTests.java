package io.jay.tddspringbootorderinsideout.authentication;

import io.jay.tddspringbootorderinsideout.authentication.rest.dto.TokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
public class EndpointAccessTests {

    @Autowired
    private MockMvc mockMvc;

    private AuthenticationTestHelper testHelper;

    @BeforeEach
    void setUp() {
        testHelper = new AuthenticationTestHelper(mockMvc);
    }

    @Test
    @Transactional
    void test_signup_isAccessible() throws Exception {
        testHelper.signUp("user@email.com", "password")
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void test_login_isAccessible() throws Exception {
        testHelper.signUp("user@email.com", "password");


        testHelper.login("user@email.com", "password")
                .andExpect(status().isOk());
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

    @Test
    @Transactional
    void test_getOrdersWithAccessToken_returnsOk() throws Exception {
        testHelper.signUp("user@email.com", "password");
        TokenResponse tokenResponse = testHelper.loginAndReturnToken("user@email.com", "password");


        mockMvc.perform(get("/orders")
                .header("Authorization", "Bearer " + tokenResponse.getAccessToken()))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void test_getOrdersWithRefreshToken_returnsOk() throws Exception {
        testHelper.signUp("user@email.com", "password");
        TokenResponse tokenResponse = testHelper.loginAndReturnToken("user@email.com", "password");


        mockMvc.perform(get("/orders")
                .header("Authorization", "Bearer " + tokenResponse.getRefreshToken()))
                .andExpect(status().isOk());
    }
}
