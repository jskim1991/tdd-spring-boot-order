package io.jay.tddspringbootorderinsideout.util;

import io.jay.tddspringbootorderinsideout.domain.User;
import io.jay.tddspringbootorderinsideout.domain.UserRole;

import java.util.List;

public interface APIAccessTokenGenerator {
    String createAccessToken(String email, List<UserRole> roles);

    String createRefreshToken(String email, List<UserRole> roles);
}
