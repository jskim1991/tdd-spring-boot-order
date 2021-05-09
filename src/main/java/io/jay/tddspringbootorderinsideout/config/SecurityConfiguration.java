package io.jay.tddspringbootorderinsideout.config;

import io.jay.tddspringbootorderinsideout.authentication.filter.JwtRequestFilter;
import io.jay.tddspringbootorderinsideout.share.token.JwtSecretKey;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final JwtSecretKey jwtSecretKey;

    public SecurityConfiguration(UserDetailsService userDetailsService, JwtSecretKey jwtSecretKey) {
        this.userDetailsService = userDetailsService;
        this.jwtSecretKey = jwtSecretKey;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .httpBasic()
//                .and()
//                .disable() // rest api 만 고려하여 기본 설정 해제
                .csrf().disable()

                .authorizeRequests()
                .antMatchers("/login/**", "/signup/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .addFilterBefore(new JwtRequestFilter(userDetailsService, jwtSecretKey),
                        UsernamePasswordAuthenticationFilter.class)
        ;
    }
}
