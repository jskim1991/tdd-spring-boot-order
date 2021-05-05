package io.jay.tddspringbootorderinsideout.util;

import io.jay.tddspringbootorderinsideout.domain.User;
import io.jay.tddspringbootorderinsideout.domain.UserRole;
import io.jay.tddspringbootorderinsideout.share.JsonUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class JwtTokenUtilTests {

    private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();


    // TODO: don't like the fact that test knows about secretKey
    private byte[] secretKey = "veryDifficultSecret".getBytes(StandardCharsets.UTF_8);

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder().email("user@email.com")
                .roles(Collections.singletonList(UserRole.ROLE_USER)).build();

    }

    @Test
    void test_accessToken_usesUserEmail() {
        String accessToken = jwtTokenUtil.createAccessToken(user);


        String subject = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody().getSubject();
        assertThat(subject, equalTo("user@email.com"));
    }

    @Test
    void test_accessToken_usesIssueDate() {
        String accessToken = jwtTokenUtil.createAccessToken(user);


        Date issuedAt = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody().getIssuedAt();
        assertThat(issuedAt, notNullValue());
    }

    @Test
    void test_accessToken_hasExpirationOf10Minutes() {
        String accessToken = jwtTokenUtil.createAccessToken(user);


        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody();
        Date issuedAt = claims.getIssuedAt();
        Date expiration = claims.getExpiration();
        assertThat(expiration.getTime() - issuedAt.getTime(), equalTo(10 * 60 * 1000L));
    }

    @Test
    void test_accessToken_hasUserRoles() {
        String accessToken = jwtTokenUtil.createAccessToken(user);


        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody();

        List<UserRole> roles = JsonUtil.fromJsonList(JsonUtil.toJson(claims.get("roles")), UserRole.class);
        assertThat(roles.size(), equalTo(1));
        assertThat(roles.get(0), equalTo(UserRole.ROLE_USER));
    }

    @Test
    void test_refreshToken_hasExpirationOf30Minutes() {
        String refreshToken = jwtTokenUtil.createRefreshToken(user);


        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(refreshToken).getBody();
        Date issuedAt = claims.getIssuedAt();
        Date expiration = claims.getExpiration();
        assertThat(expiration.getTime() - issuedAt.getTime(), equalTo(30 * 60 * 1000L));
    }
}
