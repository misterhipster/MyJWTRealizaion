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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class UserService {
//    private final UserRepository userRepository;
//    private final JWTService jwtService;
//    private final AuthenticationManager authenticationManager;
//    private final PasswordEncoder passwordEncoder;

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  JWTService jwtService;
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  PasswordEncoder passwordEncoder;

    public String register(UserDTO userdto) {

//        User user = User.builder()
//                .email(userdto.getEmail())
//                .password(userdto.getPassword())
//                .build();
        User user = new User();
        user.setEmail(userdto.getEmail());
        user.setPassword(userdto.getPassword());


        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user = userRepository.save(user);
        return jwtService.generateToken(user.getEmail());
    }


    public UserDetailsInfo login(UserDTO loginCred) {
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(loginCred.getEmail(), loginCred.getPassword());


            authenticationManager.authenticate(authInputToken);

            String token = jwtService.generateToken(loginCred.getEmail());


            String[] chunks = token.split("\\.");

            Base64.Decoder decoder = Base64.getUrlDecoder();

            String header = new String(decoder.decode(chunks[0]));
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
        return userRepository.findByEmail(email);
    }
}
