package com.example.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(
        title = "API_TITLE_HERE",
        description = "DESCRIPTION_HERE",
        version = "v1"
))
@SpringBootApplication
public class Server {

    public static void main(String[] args) {
        //http://localhost:8080/swagger-ui.html/index.html
        SpringApplication.run(Server.class, args);
    }

}
