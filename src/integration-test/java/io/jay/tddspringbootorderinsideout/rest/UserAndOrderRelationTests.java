package io.jay.tddspringbootorderinsideout.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jay.tddspringbootorderinsideout.order.domain.Order;
import io.jay.tddspringbootorderinsideout.authentication.domain.User;
import io.jay.tddspringbootorderinsideout.share.json.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserAndOrderRelationTests {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    private User user;
    @BeforeEach
    void setUp() throws Exception {
        user = User.builder()
                .name("Jay")
                .email("jay@email.com")
                .phone("010-1234-1234")
                .build();
        Order firstOrder = Order.builder()
                .user(user)
                .price(100)
                .build();
        Order secondOrder = Order.builder()
                .user(user)
                .price(1200)
                .build();

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)));
        mockMvc.perform(post("/orders")
                .content(mapper.writeValueAsString(firstOrder))
                .contentType(MediaType.APPLICATION_JSON));
        mockMvc.perform(post("/orders")
                .content(mapper.writeValueAsString(secondOrder))
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser
    void test_getAllUsers_returnsUserWithOrders() throws Exception {
        String json = mockMvc.perform(get("/users/" + user.getId()))
                .andReturn()
                .getResponse()
                .getContentAsString();


        User returnedUser = JsonUtil.fromJson(json, User.class);
        assertThat(returnedUser.getOrders().size(), equalTo(2));
        assertThat(returnedUser.getOrders().get(0).getUser(), nullValue());
    }

    @Test
    @WithMockUser
    void test_getAllOrders_returnsOrdersWithUser() throws Exception {
        String json = mockMvc.perform(get("/orders"))
                .andReturn()
                .getResponse()
                .getContentAsString();


        List<Order> allOrders = JsonUtil.fromJsonList(json, Order.class);
        assertThat(allOrders.get(0).getUser().getName(), equalTo("Jay"));
        assertThat(allOrders.get(1).getUser().getOrders().size(), equalTo(0));
    }
}
