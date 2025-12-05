package com.security.TryingToMakeCorrectRealizationOfJWT.controller;

import com.security.TryingToMakeCorrectRealizationOfJWT.service.UserService;
import com.security.TryingToMakeCorrectRealizationOfJWT.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    //    private final UserService userService;
    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public ResponseEntity<User> getUserDetails() {
        return ResponseEntity.ok(userService.getUserInfo());
    }
}
