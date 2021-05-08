package io.jay.tddspringbootorderinsideout.share.token;

import io.jay.tddspringbootorderinsideout.authentication.domain.UserRole;

import java.util.List;

public interface APIAccessTokenGenerator {
    String createAccessToken(String email, List<UserRole> roles);

    String createRefreshToken(String email, List<UserRole> roles);
}
