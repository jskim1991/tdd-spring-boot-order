package io.jay.tddspringbootorderinsideout.rest;

import io.jay.tddspringbootorderinsideout.domain.User;
import io.jay.tddspringbootorderinsideout.domain.UserRole;
import io.jay.tddspringbootorderinsideout.rest.dto.SignupRequestDto;
import io.jay.tddspringbootorderinsideout.store.UserStore;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class SignupController {

    private final UserStore userStore;
    private final PasswordEncoder passwordEncoder;

    public SignupController(UserStore userStore, PasswordEncoder passwordEncoder) {
        this.userStore = userStore;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequestDto signupRequestDto) {
        User savedUser = userStore.addUser(User.builder()
                .email(signupRequestDto.getEmail())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .roles(Collections.singletonList(UserRole.ROLE_USER))
                .build());
        return savedUser.getId();
    }
}
