package io.jay.tddspringbootorderinsideout.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtSecretKeyConfiguration {

    @Bean
    public String secretKey() {
        // TODO: Maybe this should come from the environment...? Spring @Value etc...
        return "veryDifficultSecret";
    }
}
