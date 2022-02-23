package com.boun.todo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/health")
@RestController
public class HealthController {

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public ResponseEntity<?> getHealth() {
        return new ResponseEntity<>("Todo app is running!", new HttpHeaders(), HttpStatus.OK);
    }
}
