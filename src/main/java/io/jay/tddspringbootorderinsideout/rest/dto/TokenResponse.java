package io.jay.tddspringbootorderinsideout.rest.dto;

import io.jay.tddspringbootorderinsideout.domain.User;
import io.jay.tddspringbootorderinsideout.util.JwtTokenUtil;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {

    private String accessToken;
    private String refreshToken;

    public TokenResponse(User user) {
        this.accessToken = JwtTokenUtil.createAccessToken(user);
        this.refreshToken = JwtTokenUtil.createRefreshToken(user);
    }
}
