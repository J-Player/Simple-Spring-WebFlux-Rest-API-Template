package com.server.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.stereotype.Component;

/**
 A classe WebResources é apenas um bean criado para resolver um
 problema associado à classe GlobalExceptionHandler, onde é utilizado.
 */
@Component
public class WebResources extends WebProperties.Resources {

    @Autowired
    public WebResources() {
        super();
    }

}
