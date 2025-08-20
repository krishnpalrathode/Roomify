package com.hotelmanagement.roomify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotelmanagement.roomify.dto.LoginRequest;
import com.hotelmanagement.roomify.dto.Response;
import com.hotelmanagement.roomify.entity.User;
import com.hotelmanagement.roomify.service.interfac.IUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> regiter(@RequestBody User user) {
        Response response = userService.register(user);

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        Response response = userService.login(loginRequest);

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
