package io.jay.tddspringbootorderinsideout.filter;

import io.jay.tddspringbootorderinsideout.util.JwtSecretKey;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtSecretKey jwtSecretKey;

    public JwtRequestFilter(UserDetailsService userDetailsService, JwtSecretKey jwtSecretKey) {
        this.userDetailsService = userDetailsService;
        this.jwtSecretKey = jwtSecretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.length() > 7) {
            String token = bearerToken.substring(7);

            String extractedEmail = Jwts.parser()
                    .setSigningKey(jwtSecretKey.getSecretKeyAsBytes())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            UserDetails user = userDetailsService.loadUserByUsername(extractedEmail);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
