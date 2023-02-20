package com.example.api.configs.exception;

import org.springframework.boot.autoconfigure.web.WebProperties;

//@Configuration
public class ExceptionConfig {

//    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }

}
