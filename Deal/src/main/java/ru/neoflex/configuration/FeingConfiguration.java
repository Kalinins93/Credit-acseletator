package ru.neoflex.configuration;

import feign.Contract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeingConfiguration {
    @Bean
    public Contract feignContract() {
        return new Contract.Default();
    }

}
