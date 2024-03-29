package io.jay.tddspringbootorderinsideout.config;

import io.jay.tddspringbootorderinsideout.share.token.JwtSecretKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtSecretKeyConfiguration {

    @Bean
    public JwtSecretKey secretKey() {
        // TODO: Maybe this should come from the environment...? Spring @Value etc...
        return new JwtSecretKey("veryDifficultSecret");
    }
}
