package io.jay.tddspringbootorderinsideout.util;

import io.jay.tddspringbootorderinsideout.domain.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
public class JwtAPIAccessTokenGenerator implements APIAccessTokenGenerator {

    private byte[] secretKey;
    private final long minutes = 60 * 1000L;
    private final long accessTokenValidTime = 10 * minutes;
    private final long refreshTokenValidTime = 30 * minutes;

    public JwtAPIAccessTokenGenerator(String secretKey) {
        this.secretKey = secretKey.getBytes(StandardCharsets.UTF_8);
    }

    public String createAccessToken(String email, List<UserRole> roles) {
        return createToken(email, roles, accessTokenValidTime);
    }

    public String createRefreshToken(String email, List<UserRole> roles) {
        return createToken(email, roles, refreshTokenValidTime);
    }

    private String createToken(String email, List<UserRole> roles, long validTime) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

}
