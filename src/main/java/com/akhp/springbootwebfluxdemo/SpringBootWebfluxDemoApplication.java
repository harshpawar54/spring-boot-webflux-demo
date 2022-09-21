package com.akhp.springbootwebfluxdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringBootWebfluxDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebfluxDemoApplication.class, args);
    }

}
