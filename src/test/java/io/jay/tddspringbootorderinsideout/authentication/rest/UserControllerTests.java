package io.jay.tddspringbootorderinsideout.authentication.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jay.tddspringbootorderinsideout.authentication.domain.User;
import io.jay.tddspringbootorderinsideout.share.json.JsonUtil;
import io.jay.tddspringbootorderinsideout.share.domain.NameValue;
import io.jay.tddspringbootorderinsideout.share.domain.NameValueList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTests {

    private MockMvc mockMvc;
    private UserStubService userStubService;
    private UserSpyService userSpyService;
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        userStubService = new UserStubService();
        userSpyService = new UserSpyService();
        mockMvc = MockMvcBuilders
                .standaloneSetup(new UserController(userStubService))
                .build();
    }

    @Test
    void test_getAll_returnsOk() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    void test_getAll_returnsList() throws Exception {
        userStubService.setGetAll_returnValue(
                Arrays.asList(new User[]{
                        User.builder().name("John").build(),
                        User.builder().name("Sam").build()
                }));


        String json = mockMvc.perform(get("/users"))
                .andReturn()
                .getResponse()
                .getContentAsString();


        List<User> users = JsonUtil.fromJsonList(json, User.class);
        assertThat(users.size(), equalTo(2));
    }

    @Test
    void test_getUser_returnsOk() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    void test_getUser_returnsUser() throws Exception {
        userStubService.setGet_returnValue(User.builder().name("John").build());


        mockMvc.perform(get("/users/1"))
                .andExpect(jsonPath("$.name", equalTo("John")))
        ;
    }

    @Test
    void test_getUser_usesCorrectParameter() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new UserController(userSpyService))
                .build();


        mockMvc.perform(get("/users/999"));


        assertThat(userSpyService.getGet_argument_id(), equalTo("999"));
    }

    @Test
    void test_addUser_returnsOk() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void test_addUser_returnsUser() throws Exception {
        userStubService.setAdd_returnValue(User.builder().name("John").build());


        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(jsonPath("$.name", equalTo("John")))
        ;
    }

    @Test
    void test_addUser_usesCorrectParameter() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new UserController(userSpyService))
                .build();


        mockMvc.perform(post("/users")
                .content(mapper.writeValueAsString(User.builder().name("John").build()))
                .contentType(MediaType.APPLICATION_JSON));


        User add_argument_body = userSpyService.getAdd_argument_body();
        assertThat(add_argument_body.getName(), equalTo("John"));
    }

    @Test
    void test_updateUser_returnsOk() throws Exception {
        mockMvc.perform(patch("/users/1")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void test_updateUser_returnsUser() throws Exception {
        userStubService.setUpdate_returnValue(User.builder().name("John").email("john@email.com").build());


        mockMvc.perform(patch("/users/1")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo("John")))
                .andExpect(jsonPath("$.email", equalTo("john@email.com")))
        ;
    }

    @Test
    void test_updateUser_usesCorrectParameters() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new UserController(userSpyService))
                .build();
        NameValueList nameValueList = new NameValueList();
        nameValueList.add(new NameValue("email", "john@email.com"));


        mockMvc.perform(patch("/users/999")
                .content(mapper.writeValueAsString(nameValueList))
                .contentType(MediaType.APPLICATION_JSON));


        assertThat(userSpyService.getUpdate_argument_id(), equalTo("999"));
        NameValue nameValue = userSpyService.getUpdate_argument_body().getNameValues().get(0);
        assertThat(nameValue.getName(), equalTo("email"));
        assertThat(nameValue.getValue(), equalTo("john@email.com"));
    }

    @Test
    void test_delete_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void test_delete_usesCorrectParameter() throws Exception {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new UserController(userSpyService))
                .build();


        mockMvc.perform(delete("/users/999"));


        assertThat(userSpyService.getDelete_argument_id(), equalTo("999"));
    }
}
