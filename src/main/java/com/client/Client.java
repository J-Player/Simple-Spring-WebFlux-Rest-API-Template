package com.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import javax.validation.constraints.NotEmpty;

@SpringBootApplication
public class Client {

    @NotEmpty
    private final String username = "";
    @NotEmpty
    private final String password = "";

    @Bean
    private WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8081")
                .defaultHeaders(headers -> {
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.setBasicAuth(username, password);
                })
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(Client.class, args);
    }

}
