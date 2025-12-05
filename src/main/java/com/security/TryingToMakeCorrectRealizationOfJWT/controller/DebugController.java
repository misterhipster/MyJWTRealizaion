package com.security.TryingToMakeCorrectRealizationOfJWT.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.security.TryingToMakeCorrectRealizationOfJWT.dto.UserDTO;
import com.security.TryingToMakeCorrectRealizationOfJWT.jwt.JWTService;
import com.security.TryingToMakeCorrectRealizationOfJWT.model.User;
import com.security.TryingToMakeCorrectRealizationOfJWT.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/debug")
//@RequiredArgsConstructor
public class DebugController {
    //    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JWTService jwtService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTService jwtService;


    @PostMapping("/check-password")
    public String checkPassword(@RequestBody UserDTO loginCred) {
        User user = userRepository.findByEmail(loginCred.getEmail());

        if (user == null) {
            return "User not found";
        }

        boolean matches = passwordEncoder.matches(
                loginCred.getPassword(),
                user.getPassword()
        );

        return "User: " + user.getEmail() + " Stored hash: " + user.getPassword() + " Password matches: " + matches;
    }


    @GetMapping("/decode-token")
    public ResponseEntity<?> decodeToken(@RequestParam String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);

            Map<String, Object> result = new HashMap<>();
            result.put("subject", jwt.getSubject());
            result.put("email_claim", jwt.getClaim("email").asString());
            result.put("issuer", jwt.getIssuer());
            result.put("expiresAt", jwt.getExpiresAt());
            result.put("issuedAt", jwt.getIssuedAt());
            result.put("all_claims", jwt.getClaims());

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", e.getMessage())
            );
        }
    }

    @GetMapping("/generate-test-token")
    public ResponseEntity<?> generateTestToken(@RequestParam String email) {
        try {
            String token = jwtService.generateToken(email);
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", e.getMessage())
            );
        }
    }
}