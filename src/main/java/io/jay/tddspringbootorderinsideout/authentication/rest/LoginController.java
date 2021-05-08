package io.jay.tddspringbootorderinsideout.authentication.rest;

import io.jay.tddspringbootorderinsideout.authentication.domain.User;
import io.jay.tddspringbootorderinsideout.authentication.rest.dto.LoginRequestDto;
import io.jay.tddspringbootorderinsideout.authentication.rest.dto.TokenResponse;
import io.jay.tddspringbootorderinsideout.authentication.store.UserStore;
import io.jay.tddspringbootorderinsideout.share.token.EndpointAccessTokenGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final UserStore userStore;
    private final EndpointAccessTokenGenerator endpointAccessTokenGenerator;
    private final PasswordEncoder passwordEncoder;

    public LoginController(UserStore userStore, EndpointAccessTokenGenerator endpointAccessTokenGenerator, PasswordEncoder passwordEncoder) {
        this.userStore = userStore;
        this.endpointAccessTokenGenerator = endpointAccessTokenGenerator;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequestDto loginRequestDto) {
        User user = userStore.getUserByEmail(loginRequestDto.getEmail());
        if (passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword()) == false) {
            throw new RuntimeException("Invalid username or password");
        }

        return TokenResponse.builder()
                .accessToken(endpointAccessTokenGenerator.createAccessToken(user.getEmail(), user.getRolesAsString()))
                .refreshToken(endpointAccessTokenGenerator.createRefreshToken(user.getEmail(), user.getRolesAsString()))
                .build();
    }
}
