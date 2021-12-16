package com.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class Application {

    static {
        BlockHound.install();
    }

    public static void main(String[] args) {
        //http://localhost:8080/swagger-ui.html
        SpringApplication.run(Application.class, args);
    }

}
