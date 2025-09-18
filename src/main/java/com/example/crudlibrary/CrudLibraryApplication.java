package com.example.crudlibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class CrudLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudLibraryApplication.class, args);
    }

}
