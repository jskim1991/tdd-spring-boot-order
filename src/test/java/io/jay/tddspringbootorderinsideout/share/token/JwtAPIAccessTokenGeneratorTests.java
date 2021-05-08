package io.jay.tddspringbootorderinsideout.share.token;

import io.jay.tddspringbootorderinsideout.authentication.domain.User;
import io.jay.tddspringbootorderinsideout.authentication.domain.UserRole;
import io.jay.tddspringbootorderinsideout.share.json.JsonUtil;
import io.jay.tddspringbootorderinsideout.share.token.JwtAPIAccessTokenGenerator;
import io.jay.tddspringbootorderinsideout.share.token.JwtSecretKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class JwtAPIAccessTokenGeneratorTests {

    private JwtAPIAccessTokenGenerator jwtTokenGenerator;
    private JwtSecretKey jwtSecretKey;
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder().email("user@email.com")
                .roles(Collections.singletonList(UserRole.ROLE_USER)).build();
        jwtSecretKey = new JwtSecretKey("onlyTheTestKnowsThisSecret");
        jwtTokenGenerator = new JwtAPIAccessTokenGenerator(jwtSecretKey);
    }

    @Test
    void test_accessToken_usesUserEmail() {
        String accessToken = jwtTokenGenerator.createAccessToken(user.getEmail(), user.getRoles());


        String subject = Jwts
                .parser()
                .setSigningKey(jwtSecretKey.getSecretKeyAsBytes())
                .parseClaimsJws(accessToken)
                .getBody()
                .getSubject();
        assertThat(subject, equalTo("user@email.com"));
    }

    @Test
    void test_accessToken_usesIssueDate() {
        String accessToken = jwtTokenGenerator.createAccessToken(user.getEmail(), user.getRoles());


        Date issuedAt = Jwts.parser().setSigningKey(jwtSecretKey.getSecretKeyAsBytes()).parseClaimsJws(accessToken).getBody().getIssuedAt();
        assertThat(issuedAt, notNullValue());
    }

    @Test
    void test_accessToken_hasExpirationOf10Minutes() {
        String accessToken = jwtTokenGenerator.createAccessToken(user.getEmail(), user.getRoles());


        Claims claims = Jwts.parser().setSigningKey(jwtSecretKey.getSecretKeyAsBytes()).parseClaimsJws(accessToken).getBody();
        Date issuedAt = claims.getIssuedAt();
        Date expiration = claims.getExpiration();
        assertThat(expiration.getTime() - issuedAt.getTime(), equalTo(10 * 60 * 1000L));
    }

    @Test
    void test_accessToken_hasUserRoles() {
        String accessToken = jwtTokenGenerator.createAccessToken(user.getEmail(), user.getRoles());


        Claims claims = Jwts.parser().setSigningKey(jwtSecretKey.getSecretKeyAsBytes()).parseClaimsJws(accessToken).getBody();

        List<UserRole> roles = JsonUtil.fromJsonList(JsonUtil.toJson(claims.get("roles")), UserRole.class);
        assertThat(roles.size(), equalTo(1));
        assertThat(roles.get(0), equalTo(UserRole.ROLE_USER));
    }

    @Test
    void test_refreshToken_hasExpirationOf30Minutes() {
        String refreshToken = jwtTokenGenerator.createRefreshToken(user.getEmail(), user.getRoles());


        Claims claims = Jwts.parser().setSigningKey(jwtSecretKey.getSecretKeyAsBytes()).parseClaimsJws(refreshToken).getBody();
        Date issuedAt = claims.getIssuedAt();
        Date expiration = claims.getExpiration();
        assertThat(expiration.getTime() - issuedAt.getTime(), equalTo(30 * 60 * 1000L));
    }
}
