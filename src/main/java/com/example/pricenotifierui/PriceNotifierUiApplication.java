package com.example.pricenotifierui;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//@EnableJpaRepositories
@SpringBootApplication
public class PriceNotifierUiApplication {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        return restTemplate;
    }

    @Bean
    public ThreadGroup threadGroup() {
        return new ThreadGroup("PC");
    }

    public static void main(String[] args) {
        SpringApplication.run(PriceNotifierUiApplication.class, args);
    }

}
