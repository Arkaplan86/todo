package com.boun.todo.controller;


import com.boun.todo.dto.AuthRequest;
import com.boun.todo.dto.LoginResponseDto;
import com.boun.todo.dto.RegisterRequestDto;
import com.boun.todo.dto.ResponseDto;
import com.boun.todo.service.userDetails.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomUserDetailService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest auth) throws AuthenticationException {
        LoginResponseDto response = userService.loginUser(auth);
        if (!response.isStatus())
            return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto user) {
        ResponseDto response = userService.registerUser(user);
        if (!response.isStatus())
            return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);

    }

}
