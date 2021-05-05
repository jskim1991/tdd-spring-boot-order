package io.jay.tddspringbootorderinsideout.util;

import io.jay.tddspringbootorderinsideout.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private final String secretKey = "veryDifficultSecret";
    private final long minutes = 60 * 1000L;
    private final long accessTokenValidTime = 10 * minutes;
    private final long refreshTokenValidTime = 30 * minutes;

    public String createAccessToken(User user) {
        return createToken(user, accessTokenValidTime);
    }

    public String createRefreshToken(User user) {
        return createToken(user, refreshTokenValidTime);
    }

    private String createToken(User user, long validTime) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("roles", user.getRoles());
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validTime))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes(StandardCharsets.UTF_8))
                .compact();
    }
}
