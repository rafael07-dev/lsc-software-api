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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenProvider jwtTokenProvider, ConfirmationTokenRepository confirmationTokenRepository, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserAuth userAuth){
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuth.getUsername(), userAuth.getPassword()));

            UserEntity userEntity = userRepository.findByUsername(userAuth.getUsername()).orElseThrow();

            return jwtTokenProvider.createToken(userAuth.getUsername(), userEntity.getRoles());
        }catch (AuthenticationException e){
            log.error(e.getMessage());
            return "Invalid username/password";
        }
    }

    @PostMapping("/sing-up")
    public ResponseEntity<ResponseApi> register(@RequestBody UserSingUp userSingUp) {
        return ResponseEntity.ok(authService.register(userSingUp));
    }

    @GetMapping("/confirmation")
    public ResponseApi confirmation(@RequestParam("token") String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);

        if (confirmationToken != null) {
            UserEntity user = confirmationToken.getUser();
            user.setActive(true);
            userRepository.save(user);

            return new ResponseApi(200, "Account is active");
        }

        return new ResponseApi(400, "Invalid token");
    }
}
