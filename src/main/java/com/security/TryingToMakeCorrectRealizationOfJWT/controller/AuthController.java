package com.security.TryingToMakeCorrectRealizationOfJWT.controller;

import com.security.TryingToMakeCorrectRealizationOfJWT.service.UserService;
import com.security.TryingToMakeCorrectRealizationOfJWT.dto.UserDTO;
import com.security.TryingToMakeCorrectRealizationOfJWT.dto.user.UserDetailsInfo;
import com.security.TryingToMakeCorrectRealizationOfJWT.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
//    private final UserService userService;
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerHandler(@RequestBody UserDTO user) {
        return ResponseEntity.ok(userService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDetailsInfo> loginHandler(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.login(userDTO));
    }
}
