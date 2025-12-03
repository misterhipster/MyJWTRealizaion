package com.security.TryingToMakeCorrectRealizationOfJWT.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.TryingToMakeCorrectRealizationOfJWT.dto.UserDTO;
import com.security.TryingToMakeCorrectRealizationOfJWT.dto.user.UserDetailsInfo;
import com.security.TryingToMakeCorrectRealizationOfJWT.dto.user.UserDetailsInfoHeader;
import com.security.TryingToMakeCorrectRealizationOfJWT.dto.user.UserDetailsInfoPayload;
import com.security.TryingToMakeCorrectRealizationOfJWT.jwt.JWTService;
import com.security.TryingToMakeCorrectRealizationOfJWT.model.User;
import com.security.TryingToMakeCorrectRealizationOfJWT.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    public String register(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user = userRepository.save(user);
//        String token = jwtService.generateToken(user.getEmail());
//        return token;
        return jwtService.generateToken(user.getEmail());
    }


    public UserDetailsInfo login(UserDTO loginCred) {
        try {
            System.out.println("все гуд 1111");

            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(loginCred.getEmail(), loginCred.getPassword());
            System.out.println("все гуд 2222");

//            System.out.println(authInputToken.toString());

            authenticationManager.authenticate(authInputToken);
            System.out.println("все гуд 3333");

            String token = jwtService.generateToken(loginCred.getEmail());


            String[] chunks = token.split("\\.");

            Base64.Decoder decoder = Base64.getUrlDecoder();

            String header = new String(decoder.decode(chunks[0]));
            System.out.println("все гуд 4444");
            String payload = new String(decoder.decode(chunks[1]));


            return new UserDetailsInfo(
                    new ObjectMapper().readValue(header, UserDetailsInfoHeader.class),
                    new ObjectMapper().readValue(payload, UserDetailsInfoPayload.class),
                    token);
        } catch (AuthenticationException authExc) {
            authExc.printStackTrace(); // Добавьте это!
            throw new RuntimeException("Invalid Login Credentials");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserInfo() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(email); //.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
