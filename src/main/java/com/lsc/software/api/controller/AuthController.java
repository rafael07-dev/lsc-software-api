package com.lsc.software.api.controller;

import com.lsc.software.api.Dto.UserSingUp;
import com.lsc.software.api.Dto.UserAuth;
import com.lsc.software.api.model.ConfirmationToken;
import com.lsc.software.api.model.UserEntity;
import com.lsc.software.api.repository.ConfirmationTokenRepository;
import com.lsc.software.api.repository.UserRepository;
import com.lsc.software.api.response.ResponseApi;
import com.lsc.software.api.security.JwtTokenProvider;
import com.lsc.software.api.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final AuthService authService;

    public AuthController(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, ConfirmationTokenRepository confirmationTokenRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserAuth userAuth){
       UserEntity userAuthenticated = authService.login(userAuth);

       return jwtTokenProvider.createToken(userAuthenticated);

    }

    @PostMapping("/sing-up")
    public ResponseEntity<ResponseApi> register(@RequestBody UserSingUp userSingUp) {
        return ResponseEntity.ok(authService.register(userSingUp));
    }

    @GetMapping("/confirmation")
    public ResponseEntity<?> confirmation(@RequestParam("token") String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);

        if (confirmationToken != null) {
            UserEntity user = confirmationToken.getUser();
            user.setActive(true);
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create("http://localhost:5173/confirmation"))
                    .build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
