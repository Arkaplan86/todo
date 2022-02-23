package com.boun.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoApplication {

    public static void main(String[] args) {
       // System.setProperty("server.servlet.context-path", "/api");
        SpringApplication.run(TodoApplication.class, args);
    }

}
