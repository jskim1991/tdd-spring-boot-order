package io.jay.tddspringbootorderinsideout.rest;

import io.jay.tddspringbootorderinsideout.domain.User;
import io.jay.tddspringbootorderinsideout.rest.dto.LoginRequestDto;
import io.jay.tddspringbootorderinsideout.rest.dto.TokenResponse;
import io.jay.tddspringbootorderinsideout.store.UserStore;
import io.jay.tddspringbootorderinsideout.util.JwtTokenUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final UserStore userStore;
    private final JwtTokenUtil jwtTokenUtil;

    public LoginController(UserStore userStore, JwtTokenUtil jwtTokenUtil) {
        this.userStore = userStore;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequestDto loginRequestDto) {
        User user = userStore.getUserByEmail(loginRequestDto.getEmail());
        return TokenResponse.builder()
                .accessToken(jwtTokenUtil.createAccessToken(user))
                .refreshToken(jwtTokenUtil.createRefreshToken(user))
                .build();
    }
}
