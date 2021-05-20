package io.jay.tddspringbootorderinsideout.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jay.tddspringbootorderinsideout.authentication.rest.dto.LoginRequestDto;
import io.jay.tddspringbootorderinsideout.authentication.rest.dto.SignupRequestDto;
import io.jay.tddspringbootorderinsideout.authentication.rest.dto.TokenResponse;
import io.jay.tddspringbootorderinsideout.share.json.JsonUtil;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class AuthenticationTestHelper {

    private final MockMvc mockMvc;

    public AuthenticationTestHelper(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    protected ResultActions signUp(String email, String password) throws Exception {
        return mockMvc.perform(post("/signup")
                .content(new ObjectMapper().writeValueAsString(
                        SignupRequestDto.builder()
                                .email(email)
                                .password(password)
                                .build()
                ))
                .contentType(MediaType.APPLICATION_JSON));
    }

    protected ResultActions login(String email, String password) throws Exception {
        return mockMvc.perform(post("/login")
                .content(new ObjectMapper().writeValueAsString(LoginRequestDto.builder()
                                .email(email)
                                .password(password)
                                .build()
                        )
                ).contentType(MediaType.APPLICATION_JSON));
    }

    protected TokenResponse loginAndReturnToken(String email, String password) throws Exception {
        String loginReturnValueJson = mockMvc.perform(post("/login")
                .content(new ObjectMapper().writeValueAsString(LoginRequestDto.builder()
                                .email(email)
                                .password(password)
                                .build()
                        )
                ).contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();
        TokenResponse tokenResponse = JsonUtil.fromJson(loginReturnValueJson, TokenResponse.class);
        return tokenResponse;
    }
}
