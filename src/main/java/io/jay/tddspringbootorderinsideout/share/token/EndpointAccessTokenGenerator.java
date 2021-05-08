package io.jay.tddspringbootorderinsideout.share.token;

import java.util.List;

public interface EndpointAccessTokenGenerator {
    String createAccessToken(String email, List<String> roles);

    String createRefreshToken(String email, List<String> roles);
}
