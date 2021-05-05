package io.jay.tddspringbootorderinsideout.rest;

import io.jay.tddspringbootorderinsideout.domain.User;
import io.jay.tddspringbootorderinsideout.util.JwtTokenUtil;

public class SpyStubJwtTokenUtil extends JwtTokenUtil {

    private String createAccessToken_return_value;
    private String createRefreshToken_return_value;
    private User createAccessToken_argument_user;
    private User createRefreshToken_argument_user;

    @Override
    public String createAccessToken(User user) {
        createAccessToken_argument_user = user;
        return createAccessToken_return_value;
    }

    @Override
    public String createRefreshToken(User user) {
        createRefreshToken_argument_user = user;
        return createRefreshToken_return_value;
    }

    public void setCreateAccessToken_return_value(String createAccessToken_return_value) {
        this.createAccessToken_return_value = createAccessToken_return_value;
    }

    public void setCreateRefreshToken_return_value(String createRefreshToken_return_value) {
        this.createRefreshToken_return_value = createRefreshToken_return_value;
    }

    public User getCreateAccessToken_argument_user() {
        return createAccessToken_argument_user;
    }

    public User getCreateRefreshToken_argument_user() {
        return createRefreshToken_argument_user;
    }
}
