package io.jay.tddspringbootorderinsideout.share.token;

import java.util.List;

public class SpyStubEndpointAccessTokenGenerator implements EndpointAccessTokenGenerator {

    private String createAccessToken_return_value;
    private String createRefreshToken_return_value;
    private String createRefreshToken_argument_email;
    private List<String> createRefreshToken_argument_roles;
    private String createAccessToken_argument_email;
    private List<String> createAccessToken_argument_roles;

    @Override
    public String createAccessToken(String email, List<String> roles) {
        createAccessToken_argument_email = email;
        createAccessToken_argument_roles = roles;
        return createAccessToken_return_value;
    }

    @Override
    public String createRefreshToken(String email, List<String> roles) {
        createRefreshToken_argument_email = email;
        createRefreshToken_argument_roles = roles;
        return createRefreshToken_return_value;
    }

    public void setCreateAccessToken_return_value(String createAccessToken_return_value) {
        this.createAccessToken_return_value = createAccessToken_return_value;
    }

    public void setCreateRefreshToken_return_value(String createRefreshToken_return_value) {
        this.createRefreshToken_return_value = createRefreshToken_return_value;
    }

    public String getCreateRefreshToken_argument_email() {
        return createRefreshToken_argument_email;
    }

    public List<String> getCreateRefreshToken_argument_roles() {
        return createRefreshToken_argument_roles;
    }

    public String getCreateAccessToken_argument_email() {
        return createAccessToken_argument_email;
    }

    public List<String> getCreateAccessToken_argument_roles() {
        return createAccessToken_argument_roles;
    }
}
