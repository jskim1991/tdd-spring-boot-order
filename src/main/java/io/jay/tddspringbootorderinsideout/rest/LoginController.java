package io.jay.tddspringbootorderinsideout.rest;

import io.jay.tddspringbootorderinsideout.domain.User;
import io.jay.tddspringbootorderinsideout.rest.dto.LoginRequestDto;
import io.jay.tddspringbootorderinsideout.rest.dto.TokenResponse;
import io.jay.tddspringbootorderinsideout.store.UserStore;
import io.jay.tddspringbootorderinsideout.util.APIAccessTokenGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final UserStore userStore;
    private final APIAccessTokenGenerator apiAccessTokenGenerator;
    private final PasswordEncoder passwordEncoder;

    public LoginController(UserStore userStore, APIAccessTokenGenerator apiAccessTokenGenerator, PasswordEncoder passwordEncoder) {
        this.userStore = userStore;
        this.apiAccessTokenGenerator = apiAccessTokenGenerator;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequestDto loginRequestDto) {
        User user = userStore.getUserByEmail(loginRequestDto.getEmail());
        if (passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword()) == false) {
            throw new RuntimeException("Invalid username or password");
        }
        return TokenResponse.builder()
                .accessToken(apiAccessTokenGenerator.createAccessToken(user.getEmail(), user.getRoles()))
                .refreshToken(apiAccessTokenGenerator.createRefreshToken(user.getEmail(), user.getRoles()))
                .build();
    }
}
